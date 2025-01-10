package server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import server.templates.JavalinHandlebars;
import server.templates.JavalinRenderer;
import java.util.HashSet;
import java.util.Set;

public class Server implements WithSimplePersistenceUnit {

  private static final JavalinRenderer renderer = new JavalinRenderer();

  public void start() {
    var app = Javalin.create(config -> {
      initializeStaticFiles(config);
      initializeTemplating(config);
    });
    
    app.after((ctx) -> {
      entityManager().clear();
      entityManager().close();
    });

     app.before("/*", ctx -> {
      Set<String> allowedPaths = new HashSet<>();
       allowedPaths.add("/home");
      allowedPaths.add("/login");
      allowedPaths.add("/logout");
      allowedPaths.add("/");

      if (ctx.path().startsWith("/site") || allowedPaths.contains(ctx.path()) || ctx.path().contains("/assets")) {

      }else{
        ctx.redirect("/site/error");
      }
    });

// Middleware general para validar sesión y establecer atributos comunes
    app.before("/site/*", ctx -> {
      String session_id = ctx.sessionAttribute("id");

      if (session_id == null) {
        ctx.redirect("/login");
        return;
      }

      try {
        Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(session_id));

        if (usuario == null) {
          ctx.redirect("/login");
          return;
        }

        // Establece el usuario actual como atributo del contexto
        ctx.attribute("usuario", usuario);
        ctx.attribute("esAdmin", usuario.esAdmin());
      } catch (NumberFormatException e) {
        ctx.redirect("/login");
      }
    });

// Middleware para rutas de usuario
    app.before("/site/user/*", ctx -> {
      Usuario usuario = ctx.attribute("usuario");

      // Redirigir si es administrador
      if (usuario.esAdmin()) {
        ctx.attribute("esAdmin", true);
        ctx.redirect("/site/error");
        return;
      }

      // Establecer el atributo específico para el template
      ctx.attribute("esAdmin", false);
    });

// Middleware para rutas de administrador
    app.before("/site/admin/*", ctx -> {
      Usuario usuario = ctx.attribute("usuario");

      // Redirigir si NO es administrador
      if (!usuario.esAdmin()) {
        ctx.sessionAttribute("esAdmin", false);
        ctx.redirect("/site/error");
        return;
      }

      ctx.attribute("esAdmin", true);
    });

    app.before(ctx -> {
      String defaultCss = "/assets/general.css";  // CSS por defecto
      Set<String> customCssPaths = Set.of(
          "/colaboracion/",
          "/suscripcionesHeladera/",
          "/cargaColaboracionesCSV",
          "/personasVulnerables",
          "/colaboracion/nueva",
          "/reporteFalla"
      );

      if (customCssPaths.stream().noneMatch(path -> ctx.path().contains(path))) {
        ctx.attribute("generalCssPath", defaultCss);
      }
    });


    new Router().configure(app);
    app.start(9001);
  }

  private void initializeTemplating(JavalinConfig config) {
    config.fileRenderer(
        renderer.register("hbs", new JavalinHandlebars())
    );

    Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".hbs"));
    handlebars.registerHelpers(new ClassPathTemplateLoader("/templates/partials", ".hbs"));
  }

  private static void initializeStaticFiles(JavalinConfig config) {
    config.staticFiles.add(staticFileConfig -> {
      staticFileConfig.hostedPath = "/assets";
      staticFileConfig.directory = "/assets";
    });
  }

  public static void renderWithLayout(Context ctx, String viewName, Map<String, Object> model) {
    model.put("body", renderer.render(viewName, model, ctx));
    ctx.render("mainView.hbs", model);
  }
}