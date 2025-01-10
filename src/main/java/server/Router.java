package server;

import controllers.CargarCSVController;
import controllers.IncidenteController;
import controllers.ColaboracionController;
import controllers.ColaboradorController;
import controllers.HeladeraController;
import controllers.HomeController;
import controllers.LoginController;
import controllers.LogoutController;
import controllers.NotificacionesController;
import controllers.PersonaVulnerableController;
import controllers.ReporteController;
import controllers.SuscripcionController;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.Javalin;
import java.util.HashMap;

import static server.Server.renderWithLayout;


public class Router implements SimplePersistenceTest {

  public void configure(Javalin app) {

    // ========= CONTROLLERS =============
    HomeController homeController = new HomeController();
    ColaboradorController colaboradorController = new ColaboradorController();
    HeladeraController heladeraController = new HeladeraController();
    LoginController loginController = new LoginController();
    LogoutController logoutController = new LogoutController();
    IncidenteController incidenteController = new IncidenteController();
    ReporteController reporteController = new ReporteController();
    ColaboracionController colaboracionController = new ColaboracionController();
    PersonaVulnerableController personaVulnerableController = new PersonaVulnerableController();
    CargarCSVController cargarCSVController = new CargarCSVController();
    SuscripcionController suscripcionController = new SuscripcionController();
    NotificacionesController notificacionesController = new NotificacionesController();

    // ========= LOGIN ============
    app.get("/login", loginController::index);
    app.get("/",loginController::index);
    app.post("/login", loginController::login);

    // ========= LOGOUT ============
    app.post("/logout", logoutController::index);

    //========= ERROR ============
    app.get("/site/error", ctx -> {
      //ctx.render("error.hbs");
      renderWithLayout(ctx, "/partials/error.hbs", new HashMap<>());
    });

    // ========== HOME ============
    app.get("/home", homeController::index);

    /** ======== RUTAS ADMIN =========== */

    // ============= HELADERAS ==================
    app.get("/site/heladeras", heladeraController::index);
    app.get("/site/heladerasRecomendadas", heladeraController ::indexRecomendations);
    app.post("/site/heladerasRecomendadas", heladeraController ::createHacerseCargo);

    // ============== COLABORADORES =================
    app.get("/site/admin/colaboradores", colaboradorController::index);
    app.get("/site/admin/colaboradores/{id}", colaboradorController::show);

    // ============ PERSONAS VULNERABLES ================
    app.get("/site/admin/personasVulnerables", personaVulnerableController::show);
    app.get("/site/admin/personasVulnerables/{id}", personaVulnerableController::getByIdFromPersonaVuln);

    // ========== CARGAR COLABORACIONES CSV ===============
    app.get("/site/admin/cargaColaboracionesCSV", cargarCSVController::index);
    app.post("/site/admin/cargaColaboracionesCSV", cargarCSVController::create);

    //=============== COLABORACIONES =================
    app.get("/site/admin/colaboradores/{idColaborador}/colaboraciones", colaboracionController :: getByColaboradorId);
    app.get("/site/admin/colaboradores/{idColaborador}/colaboraciones/{idColaboracion}", colaboracionController :: getByIdFromColab);

    // =============== REPORTES ================
    app.get("/site/admin/reportes", reporteController::getAll);
    app.post("/site/admin/reportes", reporteController::create);
    //app.get("/site/admin/reportes/{id}", reporteController::getById);

    // ============ INCIDENTES =============
    app.get("/site/admin/incidentes", incidenteController::show);
    app.get("/site/admin/heladeras/{idHeladera}/incidentes", incidenteController::showByIdHeladera);
    app.get("/site/admin/incidentes/{id}", incidenteController::showById);

    /** ====== RUTAS USUARIO ======== */

    // ============ RECOMENDACION DE HELADERAS ====== (Hay que ver como renderizarlo)
    app.get("/site/user/heladerasRecomendadas", heladeraController::getBestLocation);

    // ============== SUSCRIPCION A HELADERAS =================
    app.get("/site/user/suscripcionesHeladera", suscripcionController::indexHeladeras);
    app.get("/site/user/suscripcionesHeladera/{idHeladera}", suscripcionController::indexSuscripciones);
    app.get("/site/user/suscripcionesHeladera/{idHeladera}/nueva", suscripcionController::indexForm);
    app.post("/site/user/suscripcionesHeladera", suscripcionController::suscribe);
    app.post("/site/user/suscripcionesHeladera/{idHeladera}/{idSuscripcion}", suscripcionController::desuscribe);

    // ============== COLABORACIONES ======================
    app.get("/site/user/colaboracion/nueva",colaboracionController::getFormData);
    app.post("/site/user/colaboraciones", colaboracionController::create);

    // ============= INCIDENTES ====================
    app.get("/site/user/reporteFalla",incidenteController::getAllHeladeras);
    app.post("/site/user/reporteFalla", incidenteController::create);

    // =========== NOTIFICACIONES ===================
    app.get("/site/user/notificaciones", notificacionesController::index);
    app.get("/site/user/notificaciones/{id}", notificacionesController::show);
    app.post("/site/user/notificaciones/{id}/aceptar", notificacionesController::create);
    app.post("/site/user/notificaciones/{id}/rechazar", notificacionesController::delete);


    // ========== RUTAS EN COMUN ===================

    // ============ COLABORACIONES =============
    app.get("/site/colaboraciones", colaboracionController :: showAll);
    app.get("/site/colaboraciones/{id}",colaboracionController::getByIdFromColab);


  }


}
