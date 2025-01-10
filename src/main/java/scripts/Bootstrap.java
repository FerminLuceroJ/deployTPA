package scripts;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.Frecuencia;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.heladera.EstadoCapacidadHeladera;
import dominio.entidades.heladera.incidentes.AlertaConexion;
import dominio.entidades.heladera.incidentes.AlertaTemperatura;
import dominio.entidades.heladera.incidentes.FallaTecnica;
import dominio.entidades.heladera.incidentes.Incidente;
import dominio.entidades.heladera.incidentes.TipoFallaConexion;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.incidentes.suscripciones.TipoSuscripcion;
import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import dominio.entidades.persona.autentificacion.Rol;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.persona.colaborador.ColaboradorJuridico;
import dominio.entidades.persona.colaborador.Rubro;
import dominio.entidades.persona.colaborador.TipoJuridico;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.entidades.tarjeta.EstadoTarjeta;
import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import dominio.entidades.vianda.Estado;
import dominio.entidades.vianda.TipoComida;
import dominio.entidades.vianda.Vianda;
import dominio.localizacion.Localizacion;
import dominio.notificador.Notificacion;
import dominio.notificador.TipoNotificacion;
import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioColaboradores;
import dominio.repositorios.RepositorioFormularios;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioIncidente;
import dominio.repositorios.RepositorioMediosDeContacto;
import dominio.repositorios.RepositorioNotificaciones;
import dominio.repositorios.RepositorioPersonaVulnerables;
import dominio.repositorios.RepositorioSugerencias;
import dominio.repositorios.RepositorioSuscripciones;
import dominio.repositorios.RepositorioTarjetasPersVulnerable;
import dominio.repositorios.RepositorioTarjetasColaborador;
import dominio.repositorios.RepositorioUsuarios;
import dominio.repositorios.RepositorioViandas;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Bootstrap implements WithSimplePersistenceUnit {
  public void init() {

      // ======== MEDIOS DE CONTACTO =======
      ContactoMail contactoMail1 = initContacto(ContactoMail.class,1L, "juanperez@gmail.com");
      ContactoMail contactoMail2 = initContacto(ContactoMail.class, 2L, "marialopez@gmail.com");
      ContactoMail contactoMail3 = initContacto(ContactoMail.class, 3L, "carlosgarcia@gmail.com");
      ContactoMail contactoMail4 = initContacto(ContactoMail.class, 4L, "inmobiliaria@gmail.com");
      ContactoMail contactoMail5 = initContacto(ContactoMail.class, 5L, "productosOrganicos@gmail.com");
      ContactoMail contactoMail6 = initContacto(ContactoMail.class, 6L, "transporteSeguro@gmail.com");

      // ======== TARJETAS COLABORADOR ======
      TarjetaColaborador tarjetaColaborador1 = initTarjetaColaborador(4L, "QQQ444", EstadoTarjeta.ENTREGADA);
      TarjetaColaborador tarjetaColaborador2 = initTarjetaColaborador(5L, "ZZZ333", EstadoTarjeta.ENTREGADA);
      TarjetaColaborador tarjetaColaborador3 = initTarjetaColaborador(6L, "QAZ111", EstadoTarjeta.ENTREGADA);

      // ======== COLABORADORES ==============
      ColaboradorHumano colaborador1 = initColaboradorHumano(1L, "Juan Pérez", "12345678", "Av. Libertador 1000", tarjetaColaborador1, contactoMail1);
      ColaboradorHumano colaborador2 = initColaboradorHumano(2L, "María López", "87654321", "Calle Mendoza 2450", tarjetaColaborador2, contactoMail2);
      ColaboradorHumano colaborador3 = initColaboradorHumano(3L, "Carlos García", "11223344", "Av. Mitre 500", tarjetaColaborador3, contactoMail3);
      ColaboradorJuridico colaborador4 = initColaboradorJuridico(3L, "Inmobiliaria Central", "Paseo Inmuebles 789", Rubro.COMERCIO, TipoJuridico.GUBERNAMENTAL, contactoMail4);
      ColaboradorJuridico colaborador5 = initColaboradorJuridico(4L, "Productos Orgánicos SA", "Ruta Nacional 40", Rubro.TEXTIL, TipoJuridico.EMPRESA, contactoMail5);
      ColaboradorJuridico colaborador6 = initColaboradorJuridico(5L, "Transporte Seguro", "Av. Logística 101", Rubro.SISTEMAS, TipoJuridico.ONG, contactoMail6);


      // ======== TARJETAS PERSONAS VULNERABLES ======
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable1 = initTarjetaPersonaVulnerable(1L,"ABC123", EstadoTarjeta.ENTREGADA, colaborador1);
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable2 = initTarjetaPersonaVulnerable(2L, "DEF456", EstadoTarjeta.ENTREGADA, colaborador2);
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable3 = initTarjetaPersonaVulnerable(3L, "GHI789", EstadoTarjeta.ENTREGADA, colaborador3);
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable4 = initTarjetaPersonaVulnerable(4L, "JKL012", EstadoTarjeta.ENTREGADA, colaborador1);
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable5 = initTarjetaPersonaVulnerable(5L, "MNO345", EstadoTarjeta.ENTREGADA, colaborador2);

      // ======== PERSONAS VULNERABLES =========
      PersonaVulnerable personaVulnerable1 = initPersonaVulnerable(1L, "Ana Pérez", "Calle Falsa 123", LocalDate.of(1985, 5, 10), 34567890, 2, true, colaborador1, tarjetaPersonaVulnerable1);
      PersonaVulnerable personaVulnerable2 = initPersonaVulnerable(2L, "Carlos Gómez", "Av. Siempre Viva 742", LocalDate.of(1990, 8, 20), 23456789, 3, false, colaborador2, tarjetaPersonaVulnerable2);
      PersonaVulnerable personaVulnerable3 = initPersonaVulnerable(3L, "Laura Sánchez", "Paseo de la Plaza 56", LocalDate.of(1978, 11, 15), 12345678, 1, false, colaborador3, tarjetaPersonaVulnerable3);
      PersonaVulnerable personaVulnerable4 = initPersonaVulnerable(4L, "Miguel Fernández", "Av. Roca 345", LocalDate.of(1995, 3, 5), 87654321, 4, true, colaborador1, tarjetaPersonaVulnerable4);
      PersonaVulnerable personaVulnerable5 = initPersonaVulnerable(5L, "Mariana López", "Calle Libertad 789", LocalDate.of(1982, 6, 25), 98765432, 0, false, colaborador2, tarjetaPersonaVulnerable5);


      // ========= SUSCRIPCIONES PARA HELADERA 1 ===================
      List<Suscripcion> suscripcionesHeladera1 = new ArrayList<>();
      suscripcionesHeladera1.add(initSuscripcion(1L, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 3, colaborador1));
      suscripcionesHeladera1.add(initSuscripcion(2L, TipoSuscripcion.REPORTE_INCIDENTE, 0, colaborador1));

      // ========= SUSCRIPCIONES PARA HELADERA 2 ===================
      List<Suscripcion> suscripcionesHeladera2 = new ArrayList<>();
      suscripcionesHeladera2.add(initSuscripcion(3L, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 5, colaborador2));

      // ========= SUSCRIPCIONES PARA HELADERA 3 ===================
      List<Suscripcion> suscripcionesHeladera3 = new ArrayList<>();
      suscripcionesHeladera3.add(initSuscripcion(4L, TipoSuscripcion.REPORTE_INCIDENTE, 0, colaborador3));

      // ========= SUSCRIPCIONES PARA HELADERA 4 ===================
      List<Suscripcion> suscripcionesHeladera4 = new ArrayList<>();
      suscripcionesHeladera4.add(initSuscripcion(5L, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 2, colaborador1));

      // ========= SUSCRIPCIONES PARA HELADERA 5 ===================
      List<Suscripcion> suscripcionesHeladera5 = new ArrayList<>();
      suscripcionesHeladera5.add(initSuscripcion(6L, TipoSuscripcion.REPORTE_INCIDENTE, 0, colaborador2));

      // ========= HELADERAS ===================
      Heladera heladera1 = initHeladera(1L, "Heladera Central", "SN1234567890", "Av. Siempreviva 742", 20, 1000, LocalDate.of(2024, 5, 12), suscripcionesHeladera1);
      Heladera heladera2 = initHeladera(2L, "Heladera Norte", "SN0987654321", "Calle Falsa 123", 15, 800, LocalDate.of(2024, 3, 15), suscripcionesHeladera2);
      Heladera heladera3 = initHeladera(3L, "Heladera Sur", "SN5678901234", "Av. Principal 456", 18, 600, LocalDate.of(2024, 1, 20), suscripcionesHeladera3);
      Heladera heladera4 = initHeladera(4L, "Heladera Este", "SN3456789012", "Calle Secundaria 789", 25, 1200, LocalDate.of(2024, 10, 5), suscripcionesHeladera4);
      Heladera heladera5 = initHeladera(5L, "Heladera Oeste", "SN2345678901", "Boulevard Independencia 101", 10, 400, LocalDate.of(2024, 8, 30), suscripcionesHeladera5);

      // =========== SUGERENCIAS ===============
      List <Heladera> heladerasDestino = new ArrayList<>();
      heladerasDestino.add(heladera4);
      heladerasDestino.add(heladera5);
      SugerenciaMoverViandas sugerencia1 = initSugerencia(1L, heladera1, heladerasDestino);
      SugerenciaMoverViandas sugerencia2 = initSugerencia(2L, heladera2, heladerasDestino);
      SugerenciaMoverViandas sugerencia3 = initSugerencia(3L, heladera3, heladerasDestino);

      // ======== NOTIFICACIONES ==============
      Notificacion notificacion1 = initNotificacion(1L, "Se solicita distribución de viandas", "Por favor, distribuir las viandas a los puntos designados.", LocalDate.of(2024, 11, 20), TipoNotificacion.MAIL, colaborador1, sugerencia1);
      Notificacion notificacion2 = initNotificacion(2L, "Heladera necesita supervisión", "La heladera requiere una revisión técnica urgente.", LocalDate.of(2024, 11, 21), TipoNotificacion.WPP, colaborador1, sugerencia2);
      Notificacion notificacion3 = initNotificacion(3L, "Heladera supero el peso", "La heladera ha excedido su capacidad de peso.", LocalDate.of(2024, 11, 22), TipoNotificacion.TELEGRAM, colaborador1, null);
      Notificacion notificacion4 = initNotificacion(4L, "Heladera rota", "La heladera ha sufrido daños y necesita reparación.", LocalDate.of(2024, 11, 23), TipoNotificacion.MAIL, colaborador2, sugerencia3);
      Notificacion notificacion5 = initNotificacion(5L, "Se esta prendiendo fuego la heladera", "La heladera está en llamas, por favor actúe de inmediato.", LocalDate.of(2024, 11, 24), TipoNotificacion.WPP, colaborador2, sugerencia1);
      Notificacion notificacion6 = initNotificacion(6L, "La heladera sufrio un desperfecto", "La heladera ha tenido un fallo técnico.", LocalDate.of(2024, 11, 25), TipoNotificacion.TELEGRAM, colaborador2, sugerencia2);
      Notificacion notificacion7 = initNotificacion(7L, "La heladera se quedo sin viandas", "La heladera se ha quedado sin viandas, por favor reabastecer.", LocalDate.of(2024, 11, 26), TipoNotificacion.MAIL, colaborador3, null);
      Notificacion notificacion8 = initNotificacion(8L, "Se estan robando la heladera", "La heladera está siendo robada, por favor intervenga.", LocalDate.of(2024, 11, 27), TipoNotificacion.WPP, colaborador3, sugerencia2);
      Notificacion notificacion9 = initNotificacion(9L, "Le rompieron el vidrio a la heladera", "El vidrio de la heladera ha sido roto.", LocalDate.of(2024, 11, 28), TipoNotificacion.TELEGRAM, colaborador3, sugerencia3);

      // ========= INCIDENTES ==================
      Incidente incidente1 = initAlertaConexion(1L, heladera1, TipoFallaConexion.FALLA_EN_PESO);
      Incidente incidente2 = initAlertaConexion(2L, heladera1, TipoFallaConexion.FALLA_EN_TEMPERATURA);
      Incidente incidente4 = initAlertaTemperatura(3L, heladera1, 5.0);
      Incidente incidente5 = initAlertaTemperatura(4L, heladera1, 10.0);
      Incidente incidente6 = initFallaTecnica(5L, "La heladera esta rota","src/main/resources/archivos/BartSimpsonParaTPA3.jpg", heladera1);

      // ========= USUARIOS =====================
      Usuario admin = initUsuario(1L, "admin@gmail.com", "dds2024", null, Rol.ADMIN_PLATAFORMA);
      Usuario usuario1 = initUsuario(2L, "juanperez@gmail.com", "dds2024", colaborador1, Rol.COLABORADOR_HUMANO);
      Usuario usuario2 = initUsuario(3L, "marialopez@gmail.com", "dds2024", colaborador2, Rol.COLABORADOR_HUMANO);
      Usuario usuario3 = initUsuario(4L, "carlosgarcia@gmail.com", "dds2024", colaborador3, Rol.COLABORADOR_HUMANO);
      Usuario usuario4 = initUsuario(5L, "inmobiliaria@gmail.com", "dds2024", colaborador4, Rol.COLABORADOR_JURIDICO);
      Usuario usuario5 = initUsuario(6L, "productosOrganicos@gmail.com", "dds2024", colaborador5, Rol.COLABORADOR_JURIDICO);
      Usuario usuario6 = initUsuario(7L, "transporteSeguro@gmail.com", "dds2024", colaborador6, Rol.COLABORADOR_JURIDICO);

      // ========= VIANDAS ===============
      Vianda vianda1 = initVianda(1L, LocalDate.now().plusDays(10), LocalDate.now(), 500, 1.2f, Estado.NO_ENTREGADA, TipoComida.NO_PERECEDERA, heladera1, colaborador1);
      Vianda vianda2 = initVianda(2L, LocalDate.now().plusDays(8), LocalDate.now().minusDays(1), 600, 1.5f, Estado.NO_ENTREGADA, TipoComida.PERECEDERA, heladera2, colaborador2);
      Vianda vianda3 = initVianda(3L, LocalDate.now().plusDays(15), LocalDate.now().minusDays(2), 450, 1.0f, Estado.NO_ENTREGADA, TipoComida.NO_PERECEDERA, heladera3, colaborador2);

      heladera1.addVianda(vianda1);
      heladera1.addVianda(vianda2);
      heladera1.addVianda(vianda3);
      heladera2.addVianda(vianda3);
      heladera2.addVianda(vianda1);


      // ========== COLABORACIONES ==============
      Colaboracion colaboracion1 = initDistribuirVianda(1L, LocalDate.now(), 1.2, colaborador1, heladera1, heladera2, 10, "La heladera estaba perdiendo frio");
      Colaboracion colaboracion2 = initDistribuirVianda(2L, LocalDate.now().minusDays(1), 1.3, colaborador2, heladera1, heladera2, 15, "La heladera tenia el vidrio roto");
      Colaboracion colaboracion3 = initDistribuirVianda(3L, LocalDate.now().minusDays(2), 1.1, colaborador3, heladera1, heladera2, 20, "La heladera estaba al limite de su capacidad");
      Colaboracion colaboracion4 = initDonarDinero(4L, LocalDate.now(), 1.4, colaborador5, 100, Frecuencia.DIARIA);
      Colaboracion colaboracion5 = initDonarDinero(5L, LocalDate.now().minusDays(1), 1.5, colaborador4, 200, Frecuencia.ANUAL);
      Colaboracion colaboracion6 = initDonarDinero(6L, LocalDate.now().minusDays(2), 1.6, colaborador6, 300, Frecuencia.SEMANAL);
      Colaboracion colaboracion7 = initDonarVianda(7L, LocalDate.now(), 1.7, colaborador1, vianda1, heladera1);
      Colaboracion colaboracion8 = initDonarVianda(8L, LocalDate.now().minusDays(1), 1.8, colaborador2, vianda2, heladera2);
      Colaboracion colaboracion9 = initDonarVianda(9L, LocalDate.now().minusDays(2), 1.9, colaborador3, vianda3, heladera3);
      Colaboracion colaboracion10 = inirHacerseCargoHeladera(10L, LocalDate.now(), 2.0, colaborador5, heladera1);
      Colaboracion colaboracion11 = inirHacerseCargoHeladera(11L, LocalDate.now().minusDays(1), 2.1, colaborador4, heladera2);
      Colaboracion colaboracion12 = inirHacerseCargoHeladera(12L, LocalDate.now().minusDays(2), 2.2, colaborador6, heladera1);
      Colaboracion colaboracion13 = initRegistroPersonaVulnerable(13L, LocalDate.now(), 2.3, colaborador1, "Descripción 1", personaVulnerable1);
      Colaboracion colaboracion14 = initRegistroPersonaVulnerable(14L, LocalDate.now().minusDays(1), 2.4, colaborador2, "Descripción 2", personaVulnerable2);
      Colaboracion colaboracion15 = initRegistroPersonaVulnerable(15L, LocalDate.now().minusDays(2), 2.5, colaborador3, "Descripción 3", personaVulnerable3);


      // Metodos asincronicos para ver que funque bien notificar


  }

  private ColaboradorHumano initColaboradorHumano(long id, String nombreYapellido, String numeroDocumento,
                                     String direccion, TarjetaColaborador tarjetaColaborador, MedioContacto medioDeContacto) {



    if (RepositorioColaboradores.getInstance().buscarPorId(id) == null) {

      List<MedioContacto> mediosDeContactos = new ArrayList<>();
      mediosDeContactos.add(medioDeContacto);

      ColaboradorHumano colaborador = new ColaboradorHumano();
      colaborador.setNombreYapellido(nombreYapellido);
      colaborador.setTipoDocumento("DNI");
      colaborador.setNumeroDocumento(numeroDocumento);
      colaborador.setDireccion(direccion);
      colaborador.setMediosDeContacto(mediosDeContactos);
      colaborador.setMedioDeContactoFavorito(medioDeContacto);
      colaborador.setLocalizacion(new Localizacion(10F,10F));
      colaborador.setTarjetaColaborador(tarjetaColaborador);
      RepositorioColaboradores.getInstance().persist(colaborador);

      return colaborador;
    }
    return RepositorioColaboradores.getInstance().buscarHumanoPorId(id);
  }

  private ColaboradorJuridico initColaboradorJuridico(long id, String razonSocial,
                                     String direccion, Rubro rubro, TipoJuridico tipoJuridico,
                                                      MedioContacto medioDeContacto) {

    if (RepositorioColaboradores.getInstance().buscarPorId(id) == null) {

      List<MedioContacto> mediosDeContactos = new ArrayList<>();
      mediosDeContactos.add(medioDeContacto);

      ColaboradorJuridico colaborador = new ColaboradorJuridico();
      colaborador.setRazonSocial(razonSocial);
      colaborador.setDireccion(direccion);
      colaborador.setMediosDeContacto(mediosDeContactos);
      colaborador.setLocalizacion(new Localizacion(10F, 10F));
      colaborador.setTipoJuridico(tipoJuridico);
      colaborador.setRubro(rubro);

      RepositorioColaboradores.getInstance().persist(colaborador);
      return colaborador;
    }

    return RepositorioColaboradores.getInstance().buscarJuridicoPorId(id);
  }

  private Heladera initHeladera(long id, String nombreSignificativo, String serialNumber, String direccion,
                            Integer capacidadViandas, Integer cantidadDeUsos, LocalDate fechaFuncionamiento, List<Suscripcion> suscripciones) {
    if (RepositorioHeladeras.getInstance().buscarPorId(id) == null) {
      Heladera heladera = new Heladera();
      heladera.setNombreSignificativo(nombreSignificativo);
      heladera.setSerialNumber(serialNumber);
      heladera.setDireccion(direccion);
      heladera.setCapacidadDeViandas(capacidadViandas);
      heladera.setCantidadUsos(cantidadDeUsos);
      heladera.setEstaActiva(true);
      heladera.setEstadoCapacidadHeladera(EstadoCapacidadHeladera.BAJO);
      heladera.setFechaFuncionamiento(fechaFuncionamiento);
      heladera.setLocalizacion(new Localizacion(15F, 15F));

      for (Suscripcion suscripcion : suscripciones) {
        suscripcion.setHeladera(heladera); // Asocia cada suscripción con la heladera
      }
      heladera.setSuscripcions(suscripciones); // Establece las suscripciones en la heladera

      RepositorioHeladeras.getInstance().persistir(heladera);

      return heladera;
    }

    return RepositorioHeladeras.getInstance().buscarPorId(id);
  }


  private TarjetaColaborador initTarjetaColaborador(long id, String codigoAlfaNumerico, EstadoTarjeta estado) {

    if (RepositorioTarjetasColaborador.getInstance().buscarPorId(id) == null) {
      TarjetaColaborador tarjetaColaborador = new TarjetaColaborador();
      tarjetaColaborador.setCodigoAlfaNumerico(codigoAlfaNumerico);
      tarjetaColaborador.setEstadoTarjeta(estado);

      RepositorioTarjetasColaborador.getInstance().persistir(tarjetaColaborador);

      return tarjetaColaborador;
    }
    return RepositorioTarjetasColaborador.getInstance().buscarPorId(id);
  }

  private TarjetaPersonaVulnerable initTarjetaPersonaVulnerable(long id, String codigoAlfaNumerico, EstadoTarjeta estado,
                                                                ColaboradorHumano colaborador) {

    if (RepositorioTarjetasPersVulnerable.getInstance().buscarPorId(id) == null) {
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable();

      tarjetaPersonaVulnerable.setCodigoAlfaNumerico(codigoAlfaNumerico);
      tarjetaPersonaVulnerable.setEstadoTarjeta(estado);
      tarjetaPersonaVulnerable.setColaboradorQueEntrego(colaborador);

      RepositorioTarjetasPersVulnerable.getInstance().persistir(tarjetaPersonaVulnerable);

      return tarjetaPersonaVulnerable;
    }
    return RepositorioTarjetasPersVulnerable.getInstance().buscarPorId(id);
  }

  private Notificacion initNotificacion(long id, String asunto, String mensaje, LocalDate fecha,
                                        TipoNotificacion tipoNotificacion, ColaboradorHumano colaborador,
                                        SugerenciaMoverViandas sugerencia) {

    if(RepositorioNotificaciones.getInstance().buscarPorId(id) == null) {
      Notificacion notificacion = new Notificacion();
      notificacion.setAsunto(asunto);
      notificacion.setMensaje(mensaje);
      notificacion.setFechaNotificacion(fecha);
      notificacion.setTipoNotificacion(tipoNotificacion);
      notificacion.setColaborador(colaborador);
      notificacion.setSugerencia(sugerencia);

      RepositorioNotificaciones.getInstance().persistir(notificacion);
      return notificacion;
    }

    return RepositorioNotificaciones.getInstance().buscarPorId(id);
  }

  private Vianda initVianda(long id, LocalDate fechaCaducidad, LocalDate fechaDonacion,
                            Integer calorias, Float peso, Estado estado, TipoComida tipoComida,
                            Heladera heladeraUbicada, Colaborador colaborador) {

    if(RepositorioViandas.getInstance().buscarPorId(id) == null) {
      Vianda vianda = new Vianda();
      vianda.setFechaCaducidad(fechaCaducidad);
      vianda.setFechaDonacion(fechaDonacion);
      vianda.setCalorias(calorias);
      vianda.setPeso(peso);
      vianda.setEstado(estado);
      vianda.setTipoComida(tipoComida);
      vianda.setHeladeraUbicada(heladeraUbicada);
      vianda.setColaborador(colaborador);
      RepositorioViandas.getInstance().persistir(vianda);

      return vianda;
    }

    return RepositorioViandas.getInstance().buscarPorId(id);
  }

  private Usuario initUsuario(long id, String email, String password, Colaborador colaborador, Rol rol) {

    if (RepositorioUsuarios.getInstance().buscarPorId(id) == null) {
      Usuario usuario = new Usuario();
      usuario.setEmail(email);
      usuario.setPassword(password);
      usuario.setColaborador(colaborador);
      usuario.setRol(rol);
      RepositorioUsuarios.getInstance().persistir(usuario);

      return usuario;
    }
    return RepositorioUsuarios.getInstance().buscarPorId(id);
  }

  private PersonaVulnerable initPersonaVulnerable(long id, String nombre, String direccion, LocalDate fechaNacimiento,
                                                  Integer numeroDocumento, Integer cantidadMenoresACargo,
                                                  Boolean situacionDeCalle, Colaborador colaboradorAsignado,
                                                  TarjetaPersonaVulnerable tarjetaPersonaVulnerable) {

    if(RepositorioPersonaVulnerables.getInstance().buscarPorId(id) == null) {
      PersonaVulnerable personaVulnerable = new PersonaVulnerable();
      personaVulnerable.setNombre(nombre);
      personaVulnerable.setDireccion(direccion);
      personaVulnerable.setFechaNacimiento(fechaNacimiento);
      personaVulnerable.setSituacionDeCalle(situacionDeCalle);
      personaVulnerable.setNumeroDocumento(numeroDocumento);
      personaVulnerable.setTarjetaPersonaVulnerable(null);
      personaVulnerable.setCantidadMenoresACargo(cantidadMenoresACargo);
      personaVulnerable.setColaboradorAsignado(colaboradorAsignado);
      personaVulnerable.setTarjetaPersonaVulnerable(tarjetaPersonaVulnerable);

      RepositorioPersonaVulnerables.getInstance().persistir(personaVulnerable);

      return personaVulnerable;
    }
      return RepositorioPersonaVulnerables.getInstance().buscarPorId(id);
  }

  private DistribuirVianda initDistribuirVianda(long id, LocalDate fechaColaboracion, double coeficiente, Colaborador colaborador,
                                                Heladera heladeraOrigen, Heladera heladeraDestino, Integer cantidadViandas, String motivo) {

    if(RepositorioColaboraciones.getInstance().buscarPorId(id) == null) {
      DistribuirVianda distribuirVianda = new DistribuirVianda();
      distribuirVianda.setFechaColaboracion(fechaColaboracion);
      distribuirVianda.setCoeficiente(coeficiente);
      distribuirVianda.setColaborador(colaborador);
      distribuirVianda.setHeladeraOrigen(heladeraOrigen);
      distribuirVianda.setHeladeraDestino(heladeraDestino);
      distribuirVianda.setCantidadViandas(cantidadViandas);
      distribuirVianda.setMotivo(motivo);
      RepositorioColaboraciones.getInstance().persistir(distribuirVianda);

      return distribuirVianda;
    }
    return (DistribuirVianda) RepositorioColaboraciones.getInstance().buscarPorId(id);
  }

  private DonarDinero initDonarDinero(long id, LocalDate fechaColaboracion, double coeficiente, Colaborador colaborador,
                                      Integer monto, Frecuencia frecuencia) {

    if(RepositorioColaboraciones.getInstance().buscarPorId(id) == null) {
      DonarDinero donarDinero = new DonarDinero();
      donarDinero.setFechaColaboracion(fechaColaboracion);
      donarDinero.setCoeficiente(coeficiente);
      donarDinero.setColaborador(colaborador);
      donarDinero.setMonto(monto);
      donarDinero.setFrecuencia(frecuencia);
      RepositorioColaboraciones.getInstance().persistir(donarDinero);

      return donarDinero;
    }

    return (DonarDinero) RepositorioColaboraciones.getInstance().buscarPorId(id);
  }

  private DonarVianda initDonarVianda(long id, LocalDate fechaColaboracion, double coeficiente, Colaborador colaborador,
                                      Vianda vianda, Heladera heladera) {

    if(RepositorioColaboraciones.getInstance().buscarPorId(id) == null) {
      DonarVianda donarVianda = new DonarVianda();
      donarVianda.setFechaColaboracion(fechaColaboracion);
      donarVianda.setCoeficiente(coeficiente);
      donarVianda.setColaborador(colaborador);
      donarVianda.setVianda(vianda);
      donarVianda.setHeladera(heladera);
      RepositorioColaboraciones.getInstance().persistir(donarVianda);

      return donarVianda;
    }

    return (DonarVianda) RepositorioColaboraciones.getInstance().buscarPorId(id);
  }

  private HacerseCargoHeladera inirHacerseCargoHeladera(long id, LocalDate fechaColaboracion, double coeficiente, Colaborador colaborador,
                                                        Heladera heladera) {

    if(RepositorioColaboraciones.getInstance().buscarPorId(id) == null) {

      HacerseCargoHeladera hacerseCargoHeladera = new HacerseCargoHeladera();
      hacerseCargoHeladera.setFechaColaboracion(fechaColaboracion);
      hacerseCargoHeladera.setCoeficiente(coeficiente);
      hacerseCargoHeladera.setColaborador(colaborador);
      hacerseCargoHeladera.setHeladera(heladera);
      RepositorioColaboraciones.getInstance().persistir(hacerseCargoHeladera);

      return hacerseCargoHeladera;
    }

    return (HacerseCargoHeladera) RepositorioColaboraciones.getInstance().buscarPorId(id);
  }

  private RegistroDePersonaVulnerable initRegistroPersonaVulnerable(long id, LocalDate fechaColaboracion, double coeficiente, Colaborador colaborador,
                                                                    String descripcion, PersonaVulnerable personaVulnerable) {

    if(RepositorioColaboraciones.getInstance().buscarPorId(id) == null) {

      RegistroDePersonaVulnerable registroDePersonaVulnerable = new RegistroDePersonaVulnerable();
      registroDePersonaVulnerable.setFechaColaboracion(fechaColaboracion);
      registroDePersonaVulnerable.setCoeficiente(coeficiente);
      registroDePersonaVulnerable.setColaborador(colaborador);
      registroDePersonaVulnerable.setDescripcion(descripcion);
      registroDePersonaVulnerable.setPersonaVulnerable(personaVulnerable);
      RepositorioColaboraciones.getInstance().persistir(registroDePersonaVulnerable);

      return registroDePersonaVulnerable;
    }

    return (RegistroDePersonaVulnerable) RepositorioColaboraciones.getInstance().buscarPorId(id);
  }

  private <T extends MedioContacto> T initContacto(Class<T> tipoContacto, long id, String contenido) {
    T medioContacto = RepositorioMediosDeContacto.getInstance().buscarPorIdyTipo(id, tipoContacto);

    if (medioContacto == null) {
      try {
        // Crea una instancia del tipo de contacto y establece el contenido
        medioContacto = tipoContacto.getDeclaredConstructor().newInstance();
        medioContacto.setContenido(contenido);
        RepositorioMediosDeContacto.getInstance().persistir(medioContacto);
      } catch (Exception e) {
        throw new RuntimeException("No se pudo inicializar el contacto", e);
      }
    }
    return medioContacto;
  }

  private SugerenciaMoverViandas initSugerencia(long id, Heladera heladeraDaniada,
                                                List <Heladera> heladerasDestino) {

    if(RepositorioSugerencias.getInstance().buscarPorId(id) == null) {
      SugerenciaMoverViandas sugerencia = new SugerenciaMoverViandas();
      sugerencia.setHeladeraDaniada(heladeraDaniada);
      sugerencia.setHeladerasParaTransferir(heladerasDestino);
      RepositorioSugerencias.getInstance().persistir(sugerencia);

      return sugerencia;
    }

    return RepositorioSugerencias.getInstance().buscarPorId(id);
  }

  private Suscripcion initSuscripcion(long id, TipoSuscripcion tipoSuscripcion, int cantidadViandas,
                                      ColaboradorHumano colaboradorHumano) {
    if(RepositorioSuscripciones.getInstance().buscarPorId(id) == null) {
      Suscripcion suscripcion = new Suscripcion();
      suscripcion.setTipoSuscripcion(tipoSuscripcion);
      suscripcion.setColaboradorHumano(colaboradorHumano);
      suscripcion.setCantidadViandasNotificacion(cantidadViandas);
      //suscripcion.setHeladera(heladera);

      RepositorioSuscripciones.getInstance().persistir(suscripcion);
      return suscripcion;
    }
    return RepositorioSuscripciones.getInstance().buscarPorId(id);
  }

  private AlertaConexion initAlertaConexion(long id, Heladera heladera, TipoFallaConexion tipoFallaConexion) {
    if(RepositorioIncidente.getInstance().buscarPorId(id) == null) {
      AlertaConexion incidente = new AlertaConexion();
      incidente.setHeladera(heladera);
      incidente.setTipoFallaConexion(tipoFallaConexion);

      RepositorioIncidente.getInstance().persistir(incidente);
      return incidente;
    }
    return (AlertaConexion) RepositorioIncidente.getInstance().buscarPorId(id);
  }

  private AlertaTemperatura initAlertaTemperatura(long id, Heladera heladera, Double temperatura) {
    if(RepositorioIncidente.getInstance().buscarPorId(id) == null) {
      AlertaTemperatura incidente = new AlertaTemperatura();
      incidente.setHeladera(heladera);
      incidente.setTemperatura(temperatura);

      RepositorioIncidente.getInstance().persistir(incidente);
      return incidente;
    }
    return (AlertaTemperatura) RepositorioIncidente.getInstance().buscarPorId(id);
  }

  private FallaTecnica initFallaTecnica(long id, String descripcion, String imagePath, Heladera heladera) {
    if(RepositorioIncidente.getInstance().buscarPorId(id) == null) {
      FallaTecnica incidente = new FallaTecnica();
      incidente.setHeladera(heladera);
      incidente.setDescripcion(descripcion);
      incidente.setImagePath(imagePath);

      RepositorioIncidente.getInstance().persistir(incidente);
      return incidente;
    }
    return (FallaTecnica) RepositorioIncidente.getInstance().buscarPorId(id);
  }

}
