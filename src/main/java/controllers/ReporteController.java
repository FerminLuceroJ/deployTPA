package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.reportes.Reporte;
import dominio.repositorios.RepositorioReportes;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.Server.renderWithLayout;

public class ReporteController implements WithSimplePersistenceUnit, TransactionalOps {

  public void getAll(Context ctx) {
    List<Reporte> reportes = RepositorioReportes.getInstance().buscar();
    Map<String, Object> model = new HashMap<>();

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(reportes, paginaActual, 4);

    if (reportes.isEmpty()) {
      model.put("message", "No hay reportes realizados por el momento");
    } else {
      model.put("reportes", paginacion.get("items"));
      model.put("paginaActual", paginacion.get("paginaActual"));
      model.put("totalPaginas", paginacion.get("totalPaginas"));
      model.put("hasItems", paginacion.get("hasItems"));
    }

    model.put("pageTitle", "Reportes de Falla");
    renderWithLayout(ctx, "/partials/admin/reportes.hbs", model);
  }

  public void getById(Context ctx) {
    String id = ctx.pathParam("id");

    Reporte reporte = RepositorioReportes.getInstance().buscarPorIdReporte(Long.parseLong(id));
    Map model = new HashMap<>();

    if(reporte == null) {
      ctx.status(404).result("No existe el reporte con el id " + id);
      ctx.redirect("/error");
      return;
    } else {
      model.put("reporte", reporte);
    }


    ctx.render("reporte.hbs", model);

  }

  public void create(Context ctx) {
    String id = ctx.sessionAttribute("id");
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(id));

    Reporte reporte = new Reporte();
    reporte.setColaborador(usuario.getColaborador());
    reporte.setFechaReporte(LocalDate.now());
    reporte.setPuntaje(0); // Example value, adjust as needed

    RepositorioReportes.getInstance().persistir(reporte);
    ctx.sessionAttribute("message", "Reporte creado exitosamente");
    ctx.redirect("/site/admin/reportes");
  }
}
