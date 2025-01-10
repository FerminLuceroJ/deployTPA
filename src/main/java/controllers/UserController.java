package controllers;

import io.javalin.http.Context;

public abstract class UserController {

  protected boolean usuarioEsAdmin(Context context){
    String rol = context.sessionAttribute("rol");

    boolean esAdmin = rol.equals("ADMIN_PLATAFORMA");

    return esAdmin;
  }

}