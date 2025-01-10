package entidades;

import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.AlertaConexion;
import dominio.entidades.heladera.incidentes.NotificadorSuscriptos;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorPeso.Reading;
import dominio.entidades.heladera.sensorPeso.SensorPesoTercero;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.heladera.sensorTemperatura.TemperatureSensorTercero;
import dominio.entidades.tecnicoHeladeras.RegistroVisita;
import dominio.entidades.tecnicoHeladeras.TecnicoHeladera;
import dominio.repositorios.RepositorioIncidente;
import dominio.repositorios.RepositorioRegistroVisita;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dominio.localizacion.Localizacion;
import dominio.notificador.mediosDeContacto.ContactoMail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.repositorios.RepositorioTecnicosHeladeras;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class IncidentesTest {
  // Repositorios
  private RepositorioIncidente repositorioFallas = RepositorioIncidente.getInstance();
  private RepositorioRegistroVisita repositorioRegistroVisita = RepositorioRegistroVisita.getInstance();
  private RepositorioTecnicosHeladeras repositorioTecnicosHeladeras = RepositorioTecnicosHeladeras.getInstance();

  private Heladera heladera;
  private AdapterTemperatura adapterTemperatura;
  private AdapterPeso adapterPeso;
  private TecnicoHeladera tecnicoHeladera;
  private TemperatureSensorTercero temperatureSensorTercero;
  private SensorPesoTercero sensorPesoTercero;
  private NotificadorSuscriptos notificadorIncidentes;

  @BeforeEach
  public void setUp() {
    temperatureSensorTercero = mock(TemperatureSensorTercero.class);
    sensorPesoTercero = mock(SensorPesoTercero.class);
    notificadorIncidentes = mock(NotificadorSuscriptos.class);
    List<Suscripcion> colaboradorHumanos = new ArrayList<>();
    doNothing().when(heladera).notificarIncidente();

    heladera = new Heladera("1234", "Heladera1", new Localizacion(2, 2),
        "Basavilbaso 1420", 400, LocalDate.now(), true, new ArrayList<>(),
        4.0, 8.0, new AdapterTemperatura(temperatureSensorTercero),
        new AdapterPeso("123", sensorPesoTercero), null, null);
    adapterTemperatura = heladera.getAdapterTemperatura();
    adapterPeso = heladera.getAdapterPeso();
    ContactoMail email = new ContactoMail();
    tecnicoHeladera = new TecnicoHeladera("Manolo", "Basavilbaso 1420",new ArrayList<>(List.of((email))), new Localizacion(2, 2), new ArrayList<>());

    repositorioTecnicosHeladeras.persistir(tecnicoHeladera);
  }

  @AfterEach
  public void tearDown() {
      repositorioTecnicosHeladeras.buscar().clear();
      repositorioFallas.buscar().clear();
      repositorioRegistroVisita.buscar().clear();
      //adapterTemperatura.getLecturas().clear();
  }

  /*@Test
  public void saltaAlertaDeTemperaturaFueraDeRangoYSeNotificaAlTecnico() {
    adapterTemperatura.getLecturas().add(new Lectura(2.0));
    double temperatura = adapterTemperatura.getLecturas().get(0).getTemperatura();
    repositorioTecnicosHeladeras.persist(tecnicoHeladera);
    heladera.alertarTemperatura(temperatura);

    Assertions.assertFalse(repositorioFallas.buscar().isEmpty());
    Assertions.assertInstanceOf(AlertaTemperatura.class, repositorioFallas.buscar().get(0));
    Assertions.assertFalse(tecnicoHeladera.getSolicitudesVisitas().isEmpty());
  }*/

  /*@Test
  public void saltaFallaDeConexionAnteLecturaDeTemperaturaHaceMasDe15Minutos() {
    Lectura lectura = new Lectura(5.0);
    lectura.setHorarioLectura(LocalDateTime.now().minusMinutes(16));
    adapterTemperatura.getLecturas().add(lectura);
    heladera.diagnosticarUltimaLeturaHaceMasDe15Mins();


    Assertions.assertFalse(repositorioFallas.buscar().isEmpty());
    Assertions.assertInstanceOf(AlertaConexion.class, repositorioFallas.buscarPorId(1));
  }*/

  @Test
  public void saltaAlertaConexionAnteExcepcionEnPesaje() {
    adapterPeso.setPesoYUnidad(new Reading(-1, "libras"));

    heladera.getPeso();

    Assertions.assertFalse(repositorioFallas.buscar().isEmpty());
    Assertions.assertInstanceOf(AlertaConexion.class, repositorioFallas.buscarPorId(1));
  }

  @Test
  public void tecnicoCargaCorrectamenteUnRegistro() {
    tecnicoHeladera.generarReporteVisita(heladera, "algo", "src/main/resources/BartSimpsonParaTPA3.jpg", true);

    Assertions.assertFalse(repositorioRegistroVisita.buscar().isEmpty());
    Assertions.assertInstanceOf(RegistroVisita.class, repositorioRegistroVisita.buscarPorId(1));
  }

  @Test
  public void heladeraSeDesactivaTrasArregloIncompleto() {
    tecnicoHeladera.generarReporteVisita(heladera, "algo", "src/main/resources/BartSimpsonParaTPA3.jpg", false);

    Assertions.assertFalse(heladera.estaActiva());
  }

  @Test
  public void heladeraSeActivaTrasArregloSatisfactorio() {
    heladera.setEstaActiva(false);
    tecnicoHeladera.generarReporteVisita(heladera, "algo", "src/main/resources/BartSimpsonParaTPA3.jpg", true);

    Assertions.assertTrue(heladera.estaActiva());
  }

}
