package controllers;

import controllers.paginador.Paginador;
import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.Frecuencia;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.persona.autentificacion.Rol;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.entidades.personasVulnerables.TipoDocumento;
import dominio.entidades.vianda.Estado;
import dominio.entidades.vianda.TipoComida;
import dominio.entidades.vianda.Vianda;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioColaboradores;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioPersonaVulnerables;
import dominio.repositorios.RepositorioUsuarios;
import dominio.repositorios.RepositorioViandas;
import io.github.flbulgarelli.jpa.extras.TransactionalOps;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.Server.renderWithLayout;

public class ColaboracionController implements WithSimplePersistenceUnit, TransactionalOps {
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static final List<Map<String, String>> LISTA_TIPOS_COLABORACION_HUMANO = Arrays.asList(
      Map.of("nombre", "Donar Dinero", "valor", "donar_dinero"),
      Map.of("nombre", "Donar Vianda", "valor", "donar_vianda"),
      Map.of("nombre", "Distribuir Vianda", "valor", "distribuir_vianda"),
      Map.of("nombre", "Registro de Persona Vulnerable", "valor", "registro_persona_vulnerable")
  );
  public static final List<Map<String, String>> LISTA_TIPOS_COLABORACION_JURIDICO = Arrays.asList(
      Map.of("nombre", "Donar Dinero", "valor", "donar_dinero"),
      Map.of("nombre", "Hacerse Cargo Heladera", "valor", "hacerse_cargo_heladera")
  );

  public void showAll(Context ctx) {
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(ctx.sessionAttribute("id")));
    List<Colaboracion> colaboraciones = null;
    String filtro = ctx.queryParam("tipo");
    Map<String, Object> model = new HashMap<>();

    if (usuario == null) {
      ctx.status(404).result("El usuario no existe");
      return;
    }

    colaboraciones = filtro != null ?
        RepositorioColaboraciones.getInstance().buscarPorTipo(filtro) :
        RepositorioColaboraciones.getInstance().buscar();

    if(!usuario.esAdmin()) {
      colaboraciones = colaboraciones.stream().
          filter(suscripcion -> suscripcion.getColaborador().equals(usuario.getColaborador())).toList();
    }

    colaboraciones = colaboraciones.stream().sorted((c1, c2) -> c2.getFechaColaboracion().
        compareTo(c1.getFechaColaboracion())).toList();

    List<Map<String, Object>> colaboracionesFormateadas = colaboraciones.stream().map(colaboracion -> {
      Map<String, Object> map = new HashMap<>();
      String nombre = formatearNombre(colaboracion.getClass().getSimpleName());
      map.put("nombre", nombre);
      map.put("colaboracion", colaboracion);
      return map;
    }).toList();

    int paginaActual = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
    Map<String, Object> paginacion = Paginador.getInstance().paginar(colaboracionesFormateadas, paginaActual, 3);

    model.put("colaboraciones", paginacion.get("items"));
    model.put("paginaActual", paginacion.get("paginaActual"));
    model.put("totalPaginas", paginacion.get("totalPaginas"));
    model.put("hasItems", paginacion.get("hasItems"));

    model.put("esColaborador", !usuario.esAdmin());
    model.put("esJuridico", usuario.esJuridico());

    model.put("pageTitle", "Colaboraciones");
    renderWithLayout(ctx, "/partials/colaboraciones.hbs", model);

  }


  public void getByColaboradorId(Context ctx) {
    Long id;

    try {
      id = Long.parseLong(ctx.pathParam("idColaborador"));
    } catch (NumberFormatException e) {
      throw new BadRequestResponse("IdColaborador invalido");
    }

    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(id);

    List<Colaboracion> colaboraciones = RepositorioColaboraciones.getInstance().buscar().stream().
        filter(colaboracion -> colaboracion.getColaborador().equals(colaborador)).toList();

    Map<String, Object> model = new HashMap<>();
    String idUsuario = ctx.sessionAttribute("id");

    if(colaboraciones.isEmpty()){
      ctx.status(200).result("El colaborador no tiene Colaboraciones");
      model.put("message", "El colaborador no tiene Colaboraciones");
    }else {
      model.put("colaboraciones", colaboraciones);
      model.put("esAdmin", true);
    }

    model.put("pageTitle", "Colaboraciones");
    renderWithLayout(ctx, "/partials/colaboracion.hbs", model);
  }

  public void getByIdFromColab(Context ctx) {
    Long idColaborador;
    Long idColaboracion;

    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(ctx.sessionAttribute("id")));


    try {
      idColaborador = Long.parseLong(ctx.pathParam("id"));
    } catch (NumberFormatException e) {
      throw new BadRequestResponse("IdColaborador invalido");
    }

    try {
      idColaboracion = Long.parseLong(ctx.pathParam("id"));
    } catch (NumberFormatException e) {
      throw new BadRequestResponse("IdColaboracion invalido");
    }

    Colaboracion colaboracion = RepositorioColaboraciones.getInstance().buscarPorId(idColaboracion);

    if(colaboracion == null) {
      ctx.status(404);
      ctx.result("Colaboracion no encontrada");
      ctx.redirect("/error");
      return;
    }
    Map<String, Object> model = new HashMap<>();
    model.put("colaboracion", colaboracion);
    model.put("pageTitle", "Colaboracion");
    renderWithLayout(ctx, "/partials/colaboracion.hbs", model);
}

  public void create(Context context) {
    String tipoColaboracion = context.formParam("tipoColaboracion");
    Colaboracion nuevaColaboracion = null;
    System.out.println("Parametros: "+context.sessionAttributeMap());
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(context.sessionAttribute("id")));
    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(usuario.getColaborador().getId());
    switch (tipoColaboracion) {
      case "donar_dinero":
        nuevaColaboracion = this.setDonarDinero(context);
        break;

      case "donar_vianda":
        nuevaColaboracion = this.setDonarVianda(context);
        break;

      case "hacerse_cargo_heladera":
        nuevaColaboracion = this.setHacerseCargoHeladera(context);
        break;

      case "distribuir_vianda":
        nuevaColaboracion = this.setDistribuirVianda(context);
        break;

      case "registro_persona_vulnerable":
        nuevaColaboracion = this.setRegistroDePersonaVulnerable(context);
        break;

      default:
        context.status(400).result("Opción no válida");
        return;
    }
    nuevaColaboracion.setColaborador(colaborador);
    nuevaColaboracion.setFechaColaboracion(LocalDate.now());

    RepositorioColaboraciones.getInstance().persistir(nuevaColaboracion);
    context.redirect("/site/user/colaboracion/nueva");
  }

  public void getFormData(Context ctx) {
    Map model = new HashMap();
    List<Heladera> heladeras = RepositorioHeladeras.getInstance().buscar();
    Usuario usuario = RepositorioUsuarios.getInstance().buscarPorId(Long.parseLong(ctx.sessionAttribute("id")));
    String tipoColaboracion = ctx.queryParam("tipoColaboracion");

    if(usuario.getRol().equals(Rol.COLABORADOR_HUMANO)){
      model.put("tipoColaboraciones",LISTA_TIPOS_COLABORACION_HUMANO);
    }else if (usuario.getRol().equals(Rol.COLABORADOR_JURIDICO)){
      model.put("tipoColaboraciones",LISTA_TIPOS_COLABORACION_JURIDICO);
    }
    model.put("tipoColaboracion", tipoColaboracion);
    model.put("heladeras", heladeras);

    model.put("cssPath", "/assets/realizarColaboracion.css");
    model.put("pageTitle", "Realizar Colaboracion");

    renderWithLayout(ctx, "/partials/user/realizarColaboracion.hbs", model);
  }

  public DonarDinero setDonarDinero(Context context){
    DonarDinero donarDinero = new DonarDinero();
    donarDinero.setFrecuencia(Frecuencia.valueOf(context.formParam("frecuencia")));
    donarDinero.setMonto(Integer.parseInt(context.formParam("monto")));
    return donarDinero;
  }

  public DonarVianda setDonarVianda(Context context){
    DonarVianda donarVianda = new DonarVianda();
    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(Long.parseLong(context.sessionAttribute("id")));
    donarVianda.setColaborador(colaborador);
    Vianda vianda = new Vianda();
    vianda.setFechaCaducidad(LocalDate.parse(context.formParam("fecha_caducidad"),formatter));
    vianda.setFechaDonacion(LocalDate.parse(context.formParam("fecha_donacion"),formatter));
    vianda.setPeso(Float.valueOf(context.formParam("peso")));
    vianda.setCalorias(Integer.parseInt(context.formParam("calorias")));
    vianda.setEstado(Estado.ENTREGADA);
    vianda.setTipoComida(TipoComida.valueOf(context.formParam("tipo_comida")));
    vianda.setColaborador(colaborador);
    donarVianda.setVianda(vianda);
    RepositorioViandas.getInstance().persistir(vianda);
    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(context.formParam("heladera")));
    donarVianda.setHeladera(heladera);

    donarVianda.setFechaColaboracion(LocalDate.parse(context.formParam("fecha_donacion"),formatter));
    return donarVianda;
  }

  public HacerseCargoHeladera setHacerseCargoHeladera(Context context){
    HacerseCargoHeladera hacerseCargoHeladera = new HacerseCargoHeladera();
    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(Long.parseLong(context.sessionAttribute("id")));
    hacerseCargoHeladera.setColaborador(colaborador);
    Heladera heladera = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(context.formParam("idHeladera")));
    hacerseCargoHeladera.setHeladera(heladera);

    return hacerseCargoHeladera;
  }

  public DistribuirVianda setDistribuirVianda(Context context){
    DistribuirVianda distribuirVianda = new DistribuirVianda();
    Heladera heladeraOrigen = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(context.formParam("heladera_origen")));
    Heladera heladeraDestino = RepositorioHeladeras.getInstance().buscarPorId(Long.parseLong(context.formParam("heladera_destino")));
    distribuirVianda.setHeladeraOrigen(heladeraOrigen);
    distribuirVianda.setHeladeraDestino(heladeraDestino);
    distribuirVianda.setCantidadViandas(Integer.parseInt(context.formParam("cantidad_viandas")));
    distribuirVianda.setMotivo(context.formParam("motivo"));

    return  distribuirVianda;
  }

  public RegistroDePersonaVulnerable setRegistroDePersonaVulnerable(Context context){
    RegistroDePersonaVulnerable registroDePersonaVulnerable = new RegistroDePersonaVulnerable();
    Colaborador colaborador = RepositorioColaboradores.getInstance().buscarPorId(Long.parseLong(context.sessionAttribute("id")));
    registroDePersonaVulnerable.setColaborador(colaborador);
    registroDePersonaVulnerable.setDescripcion(context.formParam("descripcion"));
    PersonaVulnerable personaVulnerable = new PersonaVulnerable();
    personaVulnerable.setNombre(context.formParam("nombre"));
    personaVulnerable.setTipoDocumento(TipoDocumento.valueOf(context.formParam("tipo_documento")));
    personaVulnerable.setNumeroDocumento(Integer.parseInt(context.formParam("numero_documento")));
    personaVulnerable.setFechaNacimiento(LocalDate.parse(context.formParam("fecha_nacimiento"),formatter));
    personaVulnerable.setSituacionDeCalle(Boolean.valueOf(context.formParam("situacion_de_calle")));
    personaVulnerable.setDireccion(context.formParam("direccion"));
    personaVulnerable.setCantidadMenoresACargo(Integer.parseInt(context.formParam("cantidad_menores_a_cargo")));
    personaVulnerable.setFechaIngreso(LocalDate.now());
    RepositorioPersonaVulnerables.getInstance().persistir(personaVulnerable);
    registroDePersonaVulnerable.setPersonaVulnerable(personaVulnerable);
    return registroDePersonaVulnerable;
  }

  public String formatearNombre(String nombre) {
    return nombre.replaceAll("([a-z])([A-Z])", "$1 $2");
  }


  /*public void update(Context context) {
    Long id;
    id = Long.parseLong(context.pathParam("id"));
    String tipoColaboracion = context.formParam("tipoColaboracion");

    List<Colaboracion> colaboraciones = repositorioColaboraciones.buscarPorIdColaborador(id);
    Colaboracion colaboracion = null;
    switch (tipoColaboracion) {
      case "donar_dinero":
        colaboracion = this.setDonarDinero(context);
        break;

      case "donar_vianda":
        colaboracion = this.setDonarVianda(context);
        break;

      case "hacerse_cargo_heladera":
        colaboracion = this.setHacerseCargoHeladera(context);
        break;

      case "distribuir_vianda":
        colaboracion = this.setDistribuirVianda(context);
        break;

      case "registro_persona_vulnerable":
        colaboracion = this.setRegistroDePersonaVulnerable(context);
        break;

      default:
        context.status(400).result("Opción no válida");
        return;
    }
    colaboracion.setId(id);
    RepositorioColaboraciones.getInstance().update(colaboracion);
    Map<String, Object> model = new HashMap<>();
    model.put("colaboraciones", colaboraciones);

    context.render("home.hbs", model);
  }*/
}

