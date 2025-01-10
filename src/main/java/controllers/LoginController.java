package controllers;

import dominio.repositorios.RepositorioUsuarios;
import dominio.entidades.persona.autentificacion.Usuario;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginController {

  public void index(Context context) {

    String id = context.sessionAttribute("id");

    if(id != null) {
      context.redirect("/home");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("error", context.sessionAttribute("error"));
    context.sessionAttribute("error", null);
    context.status(HttpStatus.OK);
    context.render("login.hbs", model);
  }

  public void login(Context context) {
    Usuario usuario = new Usuario();
    asignarParametros(usuario, context);

    usuario = RepositorioUsuarios.getInstance().buscarPorEmail(usuario.getEmail());

    if (usuario == null || !usuario.getPassword().equals(context.formParam("password"))) {
      recargarPagina(context);
      return;
    }

    // Credenciales correctas: asignar datos de sesión y redirigir al home
    context.sessionAttribute("id", String.valueOf(usuario.getId()));
    context.sessionAttribute("rol", String.valueOf(usuario.getRol()));
    context.redirect("home");
  }

  private void recargarPagina(Context context) {
    context.sessionAttribute("error", "Usuario o contraseña incorrectos");
    context.status(HttpStatus.FORBIDDEN);
    context.redirect("login?error=true");
  }

  private void asignarParametros(Usuario usuario, Context context) {
    if (!Objects.equals(context.formParam("email"), "")) {
      usuario.setEmail(context.formParam("email"));
    }

    if (!Objects.equals(context.formParam("password"), "")) {
      usuario.setPassword(context.formParam("password"));
    }
  }
}
