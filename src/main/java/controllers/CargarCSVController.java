package controllers;

import dominio.lectorArchivos.lecturaCSV.CargaColaboradoresCSV;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.Server.renderWithLayout;

public class CargarCSVController {

  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    model.put("pageTitle", "Cargar CSV");
    model.put("cssPath", "/assets/cargaColaboracionesCSV.css");
    renderWithLayout(context, "/partials/admin/cargaColaboracionesCSV.hbs", model);
  }

  public void create(Context context) {

    UploadedFile archivoCSV = context.uploadedFile("csvFile");
    CargaColaboradoresCSV cargaColaboradoresCSV = null;

    try {
      cargaColaboradoresCSV = new CargaColaboradoresCSV();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    if(archivoCSV != null) {
      assert cargaColaboradoresCSV != null;
      cargaColaboradoresCSV.cargarColaboradorCSVWeb(archivoCSV.content());

      context.status(HttpStatus.CREATED);
      context.redirect("/site/admin/cargaColaboracionesCSV");
    } else {
      context.status(HttpStatus.BAD_REQUEST);
    }
  }

}
