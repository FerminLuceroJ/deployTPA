/*package entidades;

import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.aperturas.ControladorAccesoExterno;
import dominio.entidades.vianda.Vianda;
import dominio.localizacion.Localizacion;
import dominio.entidades.heladera.aperturas.AdapterControladorAcceso;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.tarjeta.solicitudes.SolicitudTarjeta;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.repositorios.RepositorioTarjetasColaborador;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AperturaHeladerasTest {
  private TarjetaColaborador tarjetaColaborador;
  private ColaboradorHumano colaboradorHumano;
  private RepositorioMovimientos repositorioMovimientos = RepositorioMovimientos.getIntancia();
  private RepositorioTarjetasColaborador repositorioTarjetas = RepositorioTarjetasColaborador.getInstancia();
  private Heladera heladera;
  private AdapterTemperatura adapterTemperatura;
  private AdapterPeso adapterPeso;
  private ControladorAccesoExterno controladorAccesoExterno;
  private AdapterControladorAcceso adapterControladorAcceso;
  private Vianda vianda;
  private DonarVianda donarVianda;


  @BeforeEach
  public void setUp() {
    vianda = mock(Vianda.class);
    adapterTemperatura = mock(AdapterTemperatura.class);
    adapterPeso = mock(AdapterPeso.class);
    controladorAccesoExterno = mock(ControladorAccesoExterno.class);
    adapterControladorAcceso = new AdapterControladorAcceso(controladorAccesoExterno);

    heladera = new Heladera("1234", "Heladera1", new Localizacion(2, 2),
        "Basavilbaso 1420", 400, LocalDate.now(), true, new ArrayList<>(),
        4.0, 8.0, adapterTemperatura, adapterPeso, adapterControladorAcceso, null);
    donarVianda = new DonarVianda(vianda, heladera, colaboradorHumano);
    tarjetaColaborador = new TarjetaColaborador("1234");
    colaboradorHumano = new ColaboradorHumano("Manuel", "Cabanas","Dni", "43971777", "123", null,  new ArrayList<>(), repositorioMovimientos);
    colaboradorHumano.setTarjetaColaborador(tarjetaColaborador);
  }

  @AfterEach
  public void tearDown() {
    repositorioMovimientos.getSolicitudesDeApertura().clear();
    repositorioTarjetas.getSolicitudesDeTarjeta().clear();
  }

  @Test
  public void colaboradorSolicitaTarjetaCorrectamente() {
    colaboradorHumano.solicitarTarjeta();

    Assertions.assertFalse(repositorioTarjetas.getSolicitudesDeTarjeta().isEmpty());
  }

  @Test
  public void seAsignaCorrectamenteTarjetaAColaborador() {
    colaboradorHumano.solicitarTarjeta();
    SolicitudTarjeta solicitud = repositorioTarjetas.getSolicitudesDeTarjeta().get(0);
    solicitud.aceptarSolicitud(tarjetaColaborador);

    Assertions.assertSame(colaboradorHumano.getTarjetaColaborador(), tarjetaColaborador);
  }

  @Test
  public void seCargaCorrectamenteReporteDeApertura() {
    colaboradorHumano.solicitarAperturaHeladera(heladera, donarVianda);

    Assertions.assertFalse(repositorioMovimientos.getSolicitudesDeApertura().isEmpty());
  }

  @Test
  public void seRealizaColaboracionAlAbrirHeladera() {
    colaboradorHumano.solicitarAperturaHeladera(heladera, new DonarVianda(LocalDate.now(), vianda, heladera));
    heladera.abrirHeladera(tarjetaColaborador);

    Assertions.assertFalse(colaboradorHumano.getColaboracionesRealizadas().isEmpty());
  }

  @Test
  public void seAbreCorrectamenteHeladeraConReporteRealizadoAnteriormente() {
    colaboradorHumano.solicitarAperturaHeladera(heladera, donarVianda);
    tarjetaColaborador.usarTarjeta(heladera);

    TarjetaColaborador tarjetaColaborador1 = repositorioMovimientos.getSolicitudesDeApertura().get(0).getTarjetaColaborador();

    Assertions.assertEquals(tarjetaColaborador1, tarjetaColaborador);
  }

  @Test
  public void seInvocaCorrectamenteNotificarTarjetaHabilitada(){
    adapterControladorAcceso.notificarTarjetasColaboradorHabilitada(tarjetaColaborador);
    verify(controladorAccesoExterno).notificarTarjetasColaboradorHabilitada(tarjetaColaborador.getCodigoAlfanumerico());
  }

  @Test
  public void seInvocaCorrectamenteQuitarPermisoTarjeta() {
    adapterControladorAcceso.quitarPermisoTarjeta(tarjetaColaborador);
    verify(controladorAccesoExterno).quitarPermisoTarjeta(tarjetaColaborador.getCodigoAlfanumerico());
  }

  @Test
  public void seInvocaCorrectamenteAbrirHeladera() {
    adapterControladorAcceso.abrirHeladera(tarjetaColaborador);
    verify(controladorAccesoExterno).abrirHeladera(tarjetaColaborador.getCodigoAlfanumerico());
  }
}
*/
