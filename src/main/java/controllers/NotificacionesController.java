package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.notificador.Notificacion;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioNotificaciones;
import dominio.repositorios.RepositorioSugerencias;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static server.Server.renderWithLayout;

public class NotificacionesController implements WithSimplePersistenceUnit, TransactionalOps {

public void index(Context ctx) {
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(ctx.sessionAttribute("id")));
    Map<String, Object> model = new HashMap<>();

    if (usuario == null) {
        ctx.status(404).result("El usuario no existe");
        return;
    }

  String startDateStr = ctx.queryParam("startDate");
  String endDateStr = ctx.queryParam("endDate");
  LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty()) ? LocalDate.parse(startDateStr) : null;
  LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty()) ? LocalDate.parse(endDateStr) : null;

    List<Notificacion> notificaciones = RepositorioNotificaciones.getInstance().buscarPorIdColaborador(usuario.getColaborador().getId());

  if (startDate != null && endDate != null) {
    if (startDate.isAfter(endDate)) {
      model.put("warning", "Fecha inicio no puede ser posterior de fecha fin.");
    } else {
      notificaciones = notificaciones.stream()
          .filter(n -> !n.getFechaNotificacion().isBefore(startDate) && !n.getFechaNotificacion().isAfter(endDate))
          .collect(Collectors.toList());
    }
    model.put("queryParams", Map.of("startDate", startDateStr, "endDate", endDateStr));
  }

  int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
  Map<String, Object> paginacion = Paginador.getInstance().paginar(notificaciones, paginaActual, 3);

  model.put("notificaciones", paginacion.get("items"));
  model.put("paginaActual", paginacion.get("paginaActual"));
  model.put("totalPaginas", paginacion.get("totalPaginas"));
  model.put("hasItems", paginacion.get("hasItems"));

  model.put("pageTitle", "Notificaciones");
  renderWithLayout(ctx, "/partials/user/notificaciones.hbs", model);
}

  public void show(Context ctx) {
    Long idNotificacion;

    try {
      idNotificacion = Long.parseLong(ctx.pathParam("id"));
    } catch (NumberFormatException e) {
      throw new BadRequestResponse("IdNotificacion invalido");
    }

    Notificacion notificacion = RepositorioNotificaciones.getInstance().buscarPorId(idNotificacion);
    if (notificacion == null) {
      ctx.status(404).result("Notificación no encontrada.");
      return;
    }

    SugerenciaMoverViandas sugerencia = notificacion.getSugerencia();

    // Si la sugerencia es null, manejarlo de una manera adecuada
    List<Heladera> heladerasParaTransferir = new ArrayList<>();
    if (sugerencia != null) {
      heladerasParaTransferir = sugerencia.getHeladerasParaTransferir();
    }

    Map<String, Object> model = new HashMap<>();
    model.put("notificacion", notificacion);
    model.put("sugerencia", sugerencia);
    model.put("heladerasParaTransferir", heladerasParaTransferir);

    model.put("pageTitle", "Notificacion " + notificacion.getId().toString());
    renderWithLayout(ctx, "/partials/user/notificacion.hbs", model);
  }

  public void create(Context ctx) {
    String notificacionId = ctx.pathParam("id");
    Notificacion notificacion = RepositorioNotificaciones.getInstance().buscarPorId(Long.valueOf(notificacionId));
    if (notificacion == null) {
      ctx.status(404).result("Notificación no encontrada.");
      return;
    }

    String heladeraDestinoId = ctx.formParam("heladeraDestinoId");
    if (heladeraDestinoId == null || heladeraDestinoId.isEmpty()) {
      ctx.status(400).result("Debe seleccionar una heladera destino.");
      return;
    }

    Heladera heladeraDestino = RepositorioHeladeras.getInstance().buscarPorId(Long.valueOf(heladeraDestinoId));
    if (heladeraDestino == null) {
      ctx.status(404).result("Heladera destino no encontrada.");
      return;
    }

    Heladera heladeraDaniada = notificacion.getSugerencia().getHeladeraDaniada();

    // ===== Buscar todas las notificaciones con la misma sugerencia =====
    List<Notificacion> notificacionesConSugerencia = RepositorioNotificaciones.getInstance()
        .buscarNotifacacionesPorIdSugerencia(notificacion.getSugerencia().getId());

    // ===== Crear la colaboración =====
    DistribuirVianda distribuirVianda = new DistribuirVianda();
    distribuirVianda.setHeladeraOrigen(heladeraDaniada);
    distribuirVianda.setHeladeraDestino(heladeraDestino);
    distribuirVianda.setMotivo("Incidente en otra heladera");
    distribuirVianda.setCantidadViandas(heladeraDaniada.cantidadDeViandas());
    distribuirVianda.setFechaColaboracion(LocalDate.now());
    distribuirVianda.setColaborador(notificacion.getColaborador());

    // ===== Guardar la colaboración =====
    RepositorioColaboraciones.getInstance().persistir(distribuirVianda);
    distribuirVianda.aplicarColaboracion();

    // ===== Eliminar la sugerencia de todas las notificaciones relacionadas =====
    if (!notificacionesConSugerencia.isEmpty()) {
      for (Notificacion noti : notificacionesConSugerencia) {
        noti.setSugerencia(null);
        RepositorioNotificaciones.getInstance().actualizar(noti);
      }
    }

    ctx.status(201).redirect("/site/user/notificaciones");
  }


  public void delete(Context ctx) {
    String notificacionId = ctx.pathParam("id");
    Notificacion notificacion = RepositorioNotificaciones.getInstance().buscarPorId(Long.valueOf(notificacionId));
    if (notificacion == null) {
      ctx.status(404).result("Notificación no encontrada.");
      return;
    }

    notificacion.setSugerencia(null);
    RepositorioNotificaciones.getInstance().actualizar(notificacion);

    ctx.status(204).redirect("/site/user/notificaciones");
  }

}
