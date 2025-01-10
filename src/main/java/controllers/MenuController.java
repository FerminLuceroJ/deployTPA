package controllers;

import io.javalin.http.Context;

public class MenuController {

  public static void handleMenuSelection(Context ctx) {
    String menuOption = ctx.formParam("opciones");

    switch (menuOption) {
      case "donar_dinero":
        ctx.render("/donarDinero.hbs");
        break;
      case "donar_vianda":
        ctx.render("/donarVianda.hbs");
        break;
      case "opcion3":
        ctx.render("/opcion3.hbs");
        break;
      default:
        ctx.render("/error.hbs"); // Opcional: por si la opción no es válida
        break;
    }
  }
}