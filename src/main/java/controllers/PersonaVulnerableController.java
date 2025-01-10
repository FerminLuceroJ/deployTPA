package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.repositorios.RepositorioPersonaVulnerables;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static server.Server.renderWithLayout;

public class PersonaVulnerableController {

  public void show(Context ctx) {
    List<PersonaVulnerable> personasVulnerables = RepositorioPersonaVulnerables.getInstance().buscar();
    String filtroParametro = ctx.queryParam("nombre");
    List<PersonaVulnerable> personasVulnerablesFiltradas = Optional.ofNullable(filtroParametro)
        .map(filtro -> personasVulnerables.stream()
            .filter(personasVulnerable -> personasVulnerable.getNombre().toLowerCase().contains(filtro.toLowerCase()))
            .toList())
        .orElse(personasVulnerables);

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(personasVulnerablesFiltradas, paginaActual, 4);

    Map<String, Object> model = new HashMap<>();

    if (paginacion.isEmpty()) {
      ctx.status(200).result("No hay personas vulnerables disponibles por el momento");
      model.put("message", "No hay personas vulnerables disponibles por el momento");
    } else {
      model.put("personasVulnerables", paginacion.get("items"));
      model.put("paginaActual", paginacion.get("paginaActual"));
      model.put("totalPaginas", paginacion.get("totalPaginas"));
      model.put("hasItems", paginacion.get("hasItems"));
    }

    model.put("cssPath", "/assets/personasVulnerables.css");
    model.put("pageTitle", "Personas Vulnerables");
    renderWithLayout(ctx, "/partials/admin/personasVulnerables.hbs", model);
  }

  public void getByIdFromPersonaVuln(Context ctx) {
    String id = ctx.pathParam("id");

    PersonaVulnerable personaVulnerable = RepositorioPersonaVulnerables.getInstance().buscarPorId(Long.parseLong(id));
    Map<String, Object> model = new HashMap<>();

    if(personaVulnerable == null) {
      ctx.status(404).result("No se encontro la persona vulnerable");
      ctx.redirect("/error");
    } else {
      model.put("personaVulnerable", personaVulnerable);
    }
    model.put("pageTitle", "Persona Vulnerable");
    renderWithLayout(ctx, "/partials/admin/personaVulnerable.hbs", model);
  }
}
