package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorJuridico;
import dominio.localizacion.Localizacion;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioColaboradores;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static server.Server.renderWithLayout;

public class HeladeraController implements WithSimplePersistenceUnit, TransactionalOps {

  public void index(Context ctx) {
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();
    String filtroParametro = ctx.queryParam("nombre");
    List<Heladera> heladerasFiltradas = Optional.ofNullable(filtroParametro)
        .map(filtro -> heladeras.stream()
            .filter(heladera -> heladera.getNombreSignificativo().toLowerCase().contains(filtro.toLowerCase()))
            .toList())
        .orElse(heladeras);

    List<Map<String, Object>> heladerasConPeso = heladerasFiltradas.stream().map(heladera -> {
      Map<String, Object> map = new HashMap<>();
      map.put("heladera", heladera);
      map.put("peso", heladera.calcularPeso());
      return map;
    }).toList();

    // Pagination - calculate the total number of pages
    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(heladerasConPeso, paginaActual, 4);

    Map<String, Object> model = new HashMap<>();

    if (paginacion.isEmpty()) {
      ctx.status(200).result("No hay heladeras disponibles por el momento");
      model.put("message", "No hay heladeras disponibles por el momento");
    } else {
      model.put("heladeras", paginacion.get("items"));
      model.put("paginaActual", paginacion.get("paginaActual"));
      model.put("totalPaginas", paginacion.get("totalPaginas"));
      model.put("hasItems", paginacion.get("hasItems"));
    }
    model.put("pageTitle", "Heladeras");
    renderWithLayout(ctx, "/partials/heladeras.hbs", model);
  }

  public void indexRecomendations(Context ctx) {
    String idUsuario = ctx.sessionAttribute("id");
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(idUsuario));
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();

    if(!usuario.esJuridico()) {
      ctx.redirect("/home");
      return;
    }

    ColaboradorJuridico colaboradorJuridico = (ColaboradorJuridico) usuario.getColaborador();

    List<Heladera> heladerasACargo = RepositorioColaboraciones.getInstance().
        buscarPorIdColaborador(colaboradorJuridico.getId()).stream().
        filter(colaboracion -> colaboracion instanceof HacerseCargoHeladera)
        .map(colaboracion -> ((HacerseCargoHeladera) colaboracion).getHeladera()).toList();

    List<Heladera> interseccion = new ArrayList<>(heladeras);
    interseccion.retainAll(heladerasACargo);

    List<Heladera> resultante = new ArrayList<>(heladeras);
    resultante.addAll(heladerasACargo);
    resultante.removeAll(interseccion);

    List<Heladera> randomHeladeras;
    Collections.shuffle(resultante);
    if(resultante.size() < 4) {
      randomHeladeras = new ArrayList<>();
      randomHeladeras.addAll(resultante);
    } else {
      randomHeladeras = resultante.subList(0, 3);
    }

    Map model = new HashMap();

    if(heladeras.isEmpty()) {
      ctx.status(200).result("No hay heladeras disponibles por el momento");
      model.put("message", "No hay heladeras disponibles por el momento");
    } else {
      model.put("heladeras", randomHeladeras);
    }

    model.put("pageTitle", "Heladeras Recomendadas");
    renderWithLayout(ctx, "/partials/user/hacerseCargoHeladera.hbs", model);

  }

  public void getBestLocation(Context ctx) {
    String id = ctx.sessionAttribute("id");

    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(id));

    Localizacion localizacion = usuario.getColaborador().getLocalizacion();
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().obtenerHeladerasCercanas(localizacion);

    renderizar(ctx, heladeras);
  }

  public void createHacerseCargo(Context ctx) {
    String id = ctx.sessionAttribute("id");
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(id));
    Colaboracion nuevaColaboracion = null;
    HacerseCargoHeladera hacerseCargoHeladera = new HacerseCargoHeladera();
    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(usuario.getColaborador().getId());
    hacerseCargoHeladera.setColaborador(colaborador);
    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(ctx.formParam("idHeladera")));
    hacerseCargoHeladera.setHeladera(heladera);
    nuevaColaboracion = hacerseCargoHeladera;
    RepositorioColaboraciones.getInstance().persistir(nuevaColaboracion);
    ctx.redirect("/site/heladeras/ubicaciones");
  }

  private void renderizar(Context ctx, List<Heladera> heladeras) {
    Map<String, Object> model = new HashMap<>();
    if (heladeras.isEmpty()) {
      ctx.status(200).result("No hay heladeras por el momento.");
      model.put("message","\"No hay heladeras por el momento.");
    } else {
      model.put("heladeras", heladeras);
    }
    //ctx.render("hacerseCargoHeladera.hbs", model);
    renderWithLayout(ctx, "/partials/user/hacerseCargoHeladera.hbs", model);
  }
}


