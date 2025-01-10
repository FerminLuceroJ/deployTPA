package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.FallaTecnica;
import dominio.entidades.heladera.incidentes.Incidente;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioIncidente;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.PersistenceException;

import static server.Server.renderWithLayout;

public class IncidenteController implements WithSimplePersistenceUnit, TransactionalOps {
  public void show(Context ctx) {
    List<Incidente> incidentes = RepositorioIncidente.getInstance().buscar();
    Map<String, Object> model = new HashMap<>();
    String filtroParametro = ctx.queryParam("nombreHeladera");

    if (filtroParametro != null && !filtroParametro.isEmpty()) {
        incidentes = incidentes.stream()
            .filter(incidente -> incidente.getHeladera().getNombreSignificativo().toLowerCase().contains(filtroParametro.toLowerCase()))
            .toList();
    }

    if (incidentes.isEmpty()) {
      ctx.status(200).result("No hay incidentes.");
      model.put("message", "No hay incidentes.");
    }

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(incidentes, paginaActual, 3);

    model.put("incidentes", paginacion.get("items"));
    model.put("paginaActual", paginacion.get("paginaActual"));
    model.put("totalPaginas", paginacion.get("totalPaginas"));
    model.put("hasItems", paginacion.get("hasItems"));


    //ctx.render("incidentes.hbs", model);
    model.put("pageTitle", "Incidentes");
    renderWithLayout(ctx, "/partials/admin/incidentes.hbs", model);
  }

  public void showById(Context ctx) {
    long id;
    try {
      id = Long.parseLong(ctx.pathParam("id"));
    } catch (NumberFormatException e) {
      throw new BadRequestResponse("Id invalido");
    }

    Incidente incidente = RepositorioIncidente.getInstance().buscarPorId(id);
    Map<String, Object> model = new HashMap<>();

    if (incidente == null) {
      ctx.status(404).result("No se encuentra el incidente.");
      model.put("message", "No se encuentra el incidente.");
    } else {
      model.put("incidente", incidente);

      // Si el incidente tiene una imagen, conviértela a base64
      if (incidente.getImageData() != null) {
        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(incidente.getImageData());
        model.put("imagePath", base64Image);
      }
    }

    //ctx.render("incidente.hbs", model);
    renderWithLayout(ctx, "/partials/incidente.hbs", model);
  }

  public void showByIdHeladera(Context ctx) {
    long idHeladera;
    try {
        idHeladera = Long.parseLong(ctx.pathParam("idHeladera"));
    } catch (NumberFormatException e) {
        throw new BadRequestResponse("IdHeladera invalido");
    }

    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(idHeladera);
    List<Incidente> incidentes = RepositorioIncidente.getInstance().buscarPorHeladera(idHeladera);
    Map<String, Object> model = new HashMap<>();

    if (heladera == null) {
        ctx.status(404).result("No se encuentra la heladera.");
        model.put("message", "\"No se encuentra la heladera.");
    } else if (incidentes.isEmpty()) {
        ctx.status(200).result("\"No hay incidentes.\"");
        model.put("message", "\"No hay incidentes.\"");
    } else {
        int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int itemsPorPagina = 3; // Set the number of items per page
        Map<String, Object> paginacion = Paginador.getInstance().paginar(incidentes, paginaActual, itemsPorPagina);
        model.putAll(paginacion);
        model.put("heladera", heladera);
        model.put("incidentes", paginacion.get("items"));
        model.put("hasItems", paginacion.get("hasItems"));
    }
    //ctx.render("incidentes.hbs", model);
    model.put("pageTitle", "Incidentes");
    renderWithLayout(ctx, "/partials/admin/incidentes.hbs", model);
}

  public void create(Context ctx) {
    FallaTecnica fallaTecnica = new FallaTecnica();
    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(Objects.requireNonNull(ctx.formParam("heladera"))));
    if(heladera == null){
      ctx.status(404).result("\"No existe la heladera\"");
      return;
    }
    fallaTecnica.setDescripcion(ctx.formParam("descripcion"));
    fallaTecnica.setHeladera(heladera);

    UploadedFile uploadedFile = ctx.uploadedFile("file");
    if (uploadedFile != null) {
      try {
      String uploadDir = "ruta/a/tu/directorio/de/imagenes";
      String filePath = uploadDir + "/" + uploadedFile.filename();

      byte[] imageData = uploadedFile.content().readAllBytes();

      fallaTecnica.setImageData(imageData);

      FileUtil.streamToFile(uploadedFile.content(), filePath);
      fallaTecnica.setImagePath(filePath); // Setear el path de la imagen en la falla técnica
    } catch (IOException e) {
        ctx.status(500).result("No se pudo persistir la imagen").redirect("/site/user/reporteFalla");
      }
    }


    try {
      RepositorioIncidente.getInstance().persistir(fallaTecnica);
    } catch (PersistenceException e) {
      ctx.status(500).result("No se pudo persistir la falla tecnica").redirect("/error");
      return;
    }

    heladera.notificarIncidente();

    ctx.redirect("/site/user/reporteFalla");
  }

  public void getAllHeladeras(Context ctx) {
    Map<String,Object> model = new HashMap<>();
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();
    model.put("heladeras",heladeras);

    model.put("cssPath", "/assets/reporteFalla.css");
    model.put("pageTitle", "Reporte Fallas");

    renderWithLayout(ctx, "/partials/user/reportarFalla.hbs", model);
  }
}


