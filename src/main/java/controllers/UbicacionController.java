package controllers;

import dominio.entidades.heladera.Heladera;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioUsuarios;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UbicacionController {

  public void getAllHeladeras(Context ctx) {
    Map model = new HashMap();
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();
    model.put("heladeras",heladeras);
    ctx.render("/recomendacionUbicacionesHeladera.hbs", model);
  }
}
