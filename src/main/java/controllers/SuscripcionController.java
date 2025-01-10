package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.incidentes.suscripciones.TipoSuscripcion;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.repositorios.RepositorioColaboradores;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioSuscripciones;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static server.Server.renderWithLayout;

public class SuscripcionController implements WithSimplePersistenceUnit, TransactionalOps {

  public void indexForm(Context ctx) {
    String idHeladera = ctx.pathParam("idHeladera");
    String tipoSuscripcion = ctx.queryParam("tipoSuscripcion");
    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(idHeladera));
    List<Map<String, String>> arrayTiposDeSuscripcion = TipoSuscripcion.toArray();
    HashMap<String, Object> model = new HashMap<>();
    model.put("tipoSuscripcion",tipoSuscripcion);
    model.put("tipoSuscripciones",arrayTiposDeSuscripcion);
    model.put("heladera",heladera);

    model.put("pageTitle", "Suscripciones");
    renderWithLayout(ctx, "/partials/user/suscripcionHeladeraForm.hbs", model);
  }


  public void indexHeladeras(Context ctx) {
    String id = ctx.sessionAttribute("id");
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(id));
    String filtroParametro = ctx.queryParam("nombreHeladera");
    ColaboradorHumano colaboradorHumano;

    if(usuario.esHumano()) {
      colaboradorHumano = (ColaboradorHumano) usuario.getColaborador();
    } else {
      ctx.redirect("/home");
      return;
    }

    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();

    List<Heladera> heladerasFiltradas = Optional.ofNullable(filtroParametro)
        .map(filtro -> heladeras.stream()
            .filter(heladera -> heladera.getNombreSignificativo().toLowerCase().contains(filtro.toLowerCase()))
            .toList())
        .orElse(heladeras);

    List<Map<String, Object>> heladerasData = heladerasFiltradas.stream().map(heladera -> {
      Map<String, Object> map = new HashMap<>();
      boolean estaSuscripto = heladera.estaSuscripto(colaboradorHumano);
      map.put("estaSuscripto", estaSuscripto);
      map.put("heladera", heladera);
      return map;
    }).toList();

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(heladerasData, paginaActual, 3);

    Map<String, Object> model = new HashMap<>();

    model.put("heladeras", paginacion.get("items"));
    model.put("paginaActual", paginacion.get("paginaActual"));
    model.put("totalPaginas", paginacion.get("totalPaginas"));
    model.put("hasItems", paginacion.get("hasItems"));


    renderWithLayout(ctx, "/partials/user/suscripcionesHeladera.hbs", model);
  }

  public void indexSuscripciones(Context ctx) {
    long idColaborador = Long.parseLong(ctx.sessionAttribute("id"));
    long idHeladera = Long.parseLong(ctx.pathParam("idHeladera"));

    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(idColaborador);

    if(!usuario.esHumano()) {
      ctx.redirect("/home");
      return;
    }

    ColaboradorHumano colaboradorHumano = (ColaboradorHumano) usuario.getColaborador();

    List<Suscripcion> suscripciones = RepositorioSuscripciones.getInstance().
        buscarPorIdColaboradorYHeladera(colaboradorHumano.getId(), idHeladera);

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(suscripciones, paginaActual, 3);

    Map<String,Object> model = new HashMap<>();
    model.put("suscripciones", paginacion.get("items"));
    model.put("paginaActual", paginacion.get("paginaActual"));
    model.put("totalPaginas", paginacion.get("totalPaginas"));
    model.put("hasItems", paginacion.get("hasItems"));

    ctx.render("/usuario/suscripciones.hbs", model);
    renderWithLayout(ctx, "/partials/user/suscripciones.hbs", model);
  }

  public void suscribe(Context ctx) {
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(ctx.sessionAttribute("id")));
    ColaboradorHumano colaborador = RepositorioColaboradores.getInstance().buscarHumanoPorId(usuario.getColaborador().getId());

    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(ctx.formParam("heladera")));
    TipoSuscripcion tipoSuscripcion = TipoSuscripcion.valueOf(ctx.formParam("tipoSuscripcion"));
    Suscripcion suscripcion = new Suscripcion();

    if(tipoSuscripcion.equals(TipoSuscripcion.CANTIDAD_DE_VIANDAS)) {
      suscripcion.setCantidadViandasNotificacion(Integer.parseInt(ctx.formParam("cantidad_viandas")));
    }

    suscripcion.setTipoSuscripcion(tipoSuscripcion);
    suscripcion.setColaboradorHumano(colaborador);
    heladera.addColaboradorSuscripto(suscripcion);
    RepositorioSuscripciones.getInstance().persistir(suscripcion);
    RepositorioHeladeras.getInstance().modificar(heladera);

    ctx.redirect("/site/user/suscripcionesHeladera");

  }

  public void desuscribe(Context ctx) {
    String idUsuario = ctx.sessionAttribute("id");
    String idHeladera = ctx.pathParam("idHeladera");
    String idSuscripcion = ctx.pathParam("idSuscripcion");

    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(idUsuario));

    if(usuario.esJuridico()) {
      ctx.redirect("/home");
      return;
    }

    ColaboradorHumano colaboradorHumano = (ColaboradorHumano) usuario.getColaborador();

    Suscripcion suscripcion = RepositorioSuscripciones.getInstance().
        buscarPorIdColaboradorYHeladera(colaboradorHumano.getId(), Long.parseLong(idHeladera))
        .stream().filter(suscription -> suscription.getId() == Long.parseLong(idSuscripcion)).
        findFirst().orElse(null);

    RepositorioSuscripciones.getInstance().eliminar(suscripcion);

    ctx.redirect("/site/user/suscripcionesHeladera/"+ idHeladera);
  }


}
