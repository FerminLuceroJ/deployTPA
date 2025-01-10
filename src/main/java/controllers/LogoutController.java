package controllers;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;

public class LogoutController {

  public void index(Context ctx) {
    HttpSession session = ctx.req().getSession();
    if(session != null) {
      session.invalidate();
    }
    
    ctx.req().getSession().invalidate();
    ctx.redirect("/login");
  }
}
