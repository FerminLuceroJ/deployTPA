package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.persona.colaborador.ColaboradorJuridico;
import dominio.repositorios.RepositorioColaboradores;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static server.Server.renderWithLayout;

public class ColaboradorController implements WithSimplePersistenceUnit, TransactionalOps {

  public void index(Context ctx) {
    List<Colaborador> colaboradores = RepositorioColaboradores.getInstance().buscar();
    String filtroParametro = ctx.queryParam("nombre");

    List<Colaborador> colaboradoresFiltrados = Optional.ofNullable(filtroParametro)
        .map(filtro -> colaboradores.stream()
            .filter(colaborador -> {
              if(colaborador instanceof ColaboradorHumano humano) {
                return humano.getNombreYapellido().toLowerCase().contains(filtro.toLowerCase());
              } else if(colaborador instanceof ColaboradorJuridico juridico) {
                return juridico.getRazonSocial().toLowerCase().contains(filtro.toLowerCase());
              }
              return false;
            })
            .toList())
        .orElse(colaboradores);

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(colaboradoresFiltrados, paginaActual, 4);

    Map<String, Object> model = new HashMap<>();

    if (paginacion.isEmpty()) {
      ctx.status(200).result("No hay colaboradores disponibles por el momento");
      model.put("message", "No hay colaboradores disponibles por el momento");
    } else {
      model.put("colaboradores", paginacion.get("items"));
      model.put("paginaActual", paginacion.get("paginaActual"));
      model.put("totalPaginas", paginacion.get("totalPaginas"));
      model.put("hasItems", paginacion.get("hasItems"));
    }

    model.put("pageTitle", "Colaboradores");
    renderWithLayout(ctx, "/partials/admin/colaboradores.hbs", model);
  }

  public void show(Context ctx) {
    String id = ctx.pathParam("id");

    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(Long.parseLong(id));
    HashMap<String, Object> model = new HashMap<>();

    if(colaborador == null) {
      ctx.status(404).result("Colaborador no encontrado");
      ctx.redirect("/error");
      return;
    } else {
      if(colaborador instanceof ColaboradorHumano humano) {
        model.put("tipo", "Humano");
      } else {
        model.put("tipo", "Juridico");
      }
      model.put("colaborador", colaborador);
    }

    model.put("pageTitle", "Colaborador" + colaborador.getId());
    renderWithLayout(ctx, "/partials/admin/colaborador.hbs", model);
  }
}
