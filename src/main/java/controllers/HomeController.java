package controllers;

import dominio.entidades.persona.autentificacion.Rol;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController implements WithSimplePersistenceUnit, TransactionalOps {
  public static final List<Map<String, String>> SECCIONES_COLABORADOR_HUMANO = Arrays.asList(
      Map.of("nombre", "Mis Colaboraciones", "url", "site/mis-colaboraciones"),
      Map.of("nombre", "Reportar Falla Tecnica", "url", "site/reportar-falla"),
      Map.of("nombre", "Visualizar Reportes", "url", "site/reportes"),
      Map.of("nombre", "Suscribirse a Heladera", "url", "site/show-heladeras")
  );
  public static final List<Map<String, String>> SECCIONES_COLABORADOR_JURIDICO = Arrays.asList(
      Map.of("nombre", "Mis Colaboraciones", "url", "/mis-colaboraciones"),
      Map.of("nombre", "Reportar Falla Tecnica", "url", "/reportar-falla"),
      Map.of("nombre", "Visualizar Reportes", "url", "/reportes"),
      Map.of("nombre", "Suscribirse a Heladera", "url", "/show-heladeras"),
      Map.of("nombre", "Solicitar Recomendacion Heladera", "url", "/ubicaciones")
  );
  public void index(Context context) {
    String sessionValueId = context.sessionAttribute("id");

    Map<String, Object> model = new HashMap<>();
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(sessionValueId));
    context.redirect("/site/colaboraciones");

  }

}

