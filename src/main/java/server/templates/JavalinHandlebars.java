package server.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.FileRenderer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavalinHandlebars implements FileRenderer {

  private final Handlebars handlebars;

  public JavalinHandlebars() {
    TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
    this.handlebars = new Handlebars(loader);

    // Registrar el helper "eq"
    handlebars.registerHelper("eq", new Helper<Object>() {
      @Override
      public Object apply(Object value1, Options options) {
        Object value2 = options.param(0); // Obtener el segundo parámetro
        return value1 != null && value1.equals(value2);
      }
    });

    // Register the "isType" helper
    handlebars.registerHelper("isType", new Helper<Object>() {
      @Override
      public Object apply(Object context, Options options) {
        String type = options.param(0);
        return context != null && context.getClass().getSimpleName().equals(type);
      }
    });

    // Register the "sub" helper
    handlebars.registerHelper("sub", new Helper<Integer>() {
      @Override
      public Object apply(Integer context, Options options) {
        Integer value = options.param(0, 0);
        return context - value;
      }
    });

    // Register the "add" helper
    handlebars.registerHelper("add", new Helper<Integer>() {
      @Override
      public Object apply(Integer context, Options options) {
        Integer value = options.param(0, 0);
        return context + value;
      }
    });

    // Register the "range" helper
    handlebars.registerHelper("range", new Helper<Integer>() {
      @Override
      public Object apply(Integer context, Options options) {
        Integer end = options.param(0, 0);
        List<Integer> range = new ArrayList<>();
        for (int i = context; i <= end; i++) {
          range.add(i);
        }
        return range;
      }
    });
  }

  @NotNull
  @Override
  public String render(@NotNull String path, @NotNull Map<String, ?> model, @NotNull Context context) {
    Template template;
    try {
      // Crear un modelo combinado que incluye los atributos del contexto
      Map<String, Object> combinedModel = new HashMap<>(model);
      combinedModel.putAll(context.attributeMap());

      // Compilar y renderizar la plantilla
      template = handlebars.compile(path.replace(".hbs", ""));
      return template.apply(combinedModel);
    } catch (IOException e) {
      e.printStackTrace();
      context.status(HttpStatus.NOT_FOUND);
      return "No se encuentra la pagina indicada...";
    }
  }

}

