/*package entidades;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.Frecuencia;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.aperturas.SolicitudApertura;
import dominio.entidades.heladera.incidentes.AlertaTemperatura;
import dominio.entidades.heladera.incidentes.Incidente;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorPeso.SensorPesoTercero;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.heladera.sensorTemperatura.TemperatureSensorTercero;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.entidades.personasVulnerables.TipoDocumento;
import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.tarjeta.solicitudes.SolicitudTarjeta;
import dominio.entidades.tecnicoHeladeras.RegistroVisita;
import dominio.entidades.tecnicoHeladeras.TecnicoHeladera;
import dominio.localizacion.Localizacion;
import dominio.notificador.Notificacion;
import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioHeladeras;
import dominio.repositorios.RepositorioIncidente;
import dominio.repositorios.RepositorioNotificaciones;
import dominio.repositorios.RepositorioPersonaVulnerables;
import dominio.repositorios.RepositorioRegistroVisita;
import dominio.repositorios.RepositorioSolicitudesTarjeta;
import dominio.repositorios.RepositorioSuscripciones;
import dominio.repositorios.RepositorioTarjetasColaborador;
import dominio.repositorios.RepositorioTecnicosHeladeras;
import dominio.repositorios.RepositoriosSolicitudAperturas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class RepositoriosTest {

  private TemperatureSensorTercero temperatureSensorTercero;
  private SensorPesoTercero sensorPesoTercero;
  private RepositorioColaboradoresHumanos repositorioColaboladores = RepositorioColaboradoresHumanos.getInstance();
  private RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstance();
  private RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();
  private RepositorioNotificaciones repositorioNotificaciones = RepositorioNotificaciones.getInstance();
  private RepositorioPersonaVulnerables repositorioPersonaVulnerables = RepositorioPersonaVulnerables.getInstance();
  private RepositorioRegistroVisita repositorioRegistroVisita = RepositorioRegistroVisita.getInstance();
  private RepositorioSolicitudesTarjeta repositorioSolicitudesTarjeta = RepositorioSolicitudesTarjeta.getInstance();
  private RepositoriosSolicitudAperturas repositoriosSolicitudAperturas = RepositoriosSolicitudAperturas.getInstance();
  private RepositorioSuscripciones repositorioSuscripciones = RepositorioSuscripciones.getInstance();
  private RepositorioTarjetasColaborador repositorioTarjetasColaborador = RepositorioTarjetasColaborador.getInstance();
  private RepositorioTecnicosHeladeras repositorioTecnicosHeladeras = RepositorioTecnicosHeladeras.getInstance();
  private RepositorioColaboraciones repositorioColaboraciones = RepositorioColaboraciones.getInstance();


  Heladera heladera = new Heladera("1234", "Heladera1", new Localizacion(2, 2),
      "Basavilbaso 1420", 400, LocalDate.now(), true, new ArrayList<>(),
      4.0, 8.0, new AdapterTemperatura(temperatureSensorTercero),
      new AdapterPeso("123", sensorPesoTercero), null, null);
  ColaboradorHumano colaboradorHumano = new ColaboradorHumano(
      "Corrientes 555",
      "12345678",
      "DNI",
      "Gimenez",
      "Tomas",
      null,
      new ArrayList<MedioContacto>(),
      new ContactoWPP()
  );
  TecnicoHeladera tecnicoHeladera = new TecnicoHeladera("Manolo", "Basavilbaso 1420",new ArrayList<>(List.of((new ContactoMail()))), new Localizacion(2, 2), new ArrayList<>());

  @BeforeEach
  public void setUp() {



  }

  @Test
  public void testRepositorioColaboradores(){

    repositorioColaboladores.persistir(colaboradorHumano);

    Assertions.assertEquals(1, repositorioColaboladores.buscar().size());
  }

  @Test
  public void testRepositorioHeladeras(){
    repositorioHeladeras.persistir(heladera);

    Assertions.assertEquals(1, repositorioHeladeras.buscar().size());
  }

  @Test
  public void testRepositorioIncidente(){
    double temperatura = 15.5;
    Incidente incidente = new AlertaTemperatura(heladera, temperatura);
    repositorioHeladeras.persistir(heladera);
    repositorioIncidente.persistir(incidente);

    Assertions.assertEquals(1, repositorioIncidente.buscar().size());
  }

  @Test
  public void testRepositorioNotificaciones(){
    Notificacion notificacion = new Notificacion(colaboradorHumano, "Ocurrio un incidente en heladera nro".concat(heladera.getSerialNumber()), "Incidente heladera");
    repositorioColaboladores.persistir(colaboradorHumano);
    repositorioHeladeras.persistir(heladera);

    repositorioNotificaciones.persistir(notificacion);

    Assertions.assertEquals(1, repositorioNotificaciones.buscar().size());
  }

  @Test
  public void testRepositorioPersonasVulnerables(){
    PersonaVulnerable personaVulnerable = new PersonaVulnerable(
        "Persona",
        LocalDate.now().minusYears(15),
        true,
        "Rivadavia 2",
        TipoDocumento.DNI,
        12345,
        2,
        colaboradorHumano,
        null
    );
    repositorioColaboladores.persistir(colaboradorHumano);
    repositorioPersonaVulnerables.persistir(personaVulnerable);

    Assertions.assertEquals(1, repositorioPersonaVulnerables.buscar().size());
  }

  @Test
  public void testRepositorioTecnicosHeladeras(){

    repositorioTecnicosHeladeras.persistir(tecnicoHeladera);

    Assertions.assertEquals(1, repositorioTecnicosHeladeras.buscar().size());
  }

  @Test
  public void testRepositorioRegistroVisita(){
    RegistroVisita registroVisita = new RegistroVisita(heladera,tecnicoHeladera,"Problemas en la temperatura","",true);
    repositorioRegistroVisita.persistir(registroVisita);
    repositorioHeladeras.persistir(heladera);
    repositorioTecnicosHeladeras.persistir(tecnicoHeladera);

    Assertions.assertEquals(1, repositorioRegistroVisita.buscar().size());
  }

  @Test
  public void testRepositorioSolicitudesTarjeta(){
    SolicitudTarjeta solicitudTarjeta = new SolicitudTarjeta(colaboradorHumano);
    repositorioSolicitudesTarjeta.persistir(solicitudTarjeta);
    repositorioColaboladores.persistir(colaboradorHumano);
    Assertions.assertEquals(1, repositorioSolicitudesTarjeta.buscar().size());
  }

  @Test
  public void testRepositorioSolicitudAperturas(){
    TarjetaColaborador tarjetaColaborador = new TarjetaColaborador("123456789");
    Colaboracion donarDinero = new DonarDinero(100000, Frecuencia.MENSUAL,colaboradorHumano);
    repositorioColaboladores.persistir(colaboradorHumano);
    repositorioColaboraciones.persistir(donarDinero);
    repositorioHeladeras.persistir(heladera);

    SolicitudApertura solicitudApertura = new SolicitudApertura(heladera,colaboradorHumano,tarjetaColaborador,donarDinero);
    repositoriosSolicitudAperturas.persistir(solicitudApertura);

    Assertions.assertEquals(1, repositoriosSolicitudAperturas.buscar().size());
  }

  @Test
  public void testRepositorioSuscripciones(){
    Suscripcion suscripcionIncidente = new Suscripcion();
    repositorioSuscripciones.persistir(suscripcionIncidente);

    Assertions.assertEquals(1, repositorioSuscripciones.buscar().size());
  }

  @Test
  public void testrepositorioTarjetas(){
    Tarjeta tarjetaColaborador = new TarjetaColaborador("123456789");
    repositorioTarjetasColaborador.persistir(tarjetaColaborador);

    Assertions.assertEquals(1, repositorioTarjetasColaborador.buscar().size());
  }



}*/