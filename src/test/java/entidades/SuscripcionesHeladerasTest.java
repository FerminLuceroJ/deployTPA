/*package entidades;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.FallaTecnica;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.incidentes.suscripciones.TipoSuscripcion;
import dominio.entidades.heladera.aperturas.AdapterControladorAcceso;
import dominio.entidades.heladera.incidentes.NotificadorSuscriptos;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import java.time.LocalDate;
import java.util.ArrayList;
import dominio.localizacion.Localizacion;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.repositorios.RepositorioNotificaciones;

import static org.mockito.Mockito.mock;

public class SuscripcionesHeladerasTest {
  private ColaboradorHumano colaboradorHumano;
  private Heladera heladera;
  private NotificadorSuscriptos notificadorSuscriptos;
  private RepositorioNotificaciones repositorioNotificaciones;

  @BeforeEach
  public void setUp() {
    colaboradorHumano = new ColaboradorHumano(
        "Corrientes 555",
        "12345678",
        "DNI",
        "Juan Perez",
        null,
        new ArrayList<MedioContacto>(),
        new ContactoWPP()
    );
    colaboradorHumano.setMedioDeContactoFavorito(new ContactoWPP());
    heladera = new Heladera("1234", "Heladera1", new Localizacion(2, 2),
        "Basavilbaso 1420", 400, LocalDate.now(), true, new ArrayList<>(),
        4.0, 8.0, mock(AdapterTemperatura.class), mock(AdapterPeso.class), mock(AdapterControladorAcceso.class), null);
    notificadorSuscriptos = NotificadorSuscriptos.getInstancia();
    repositorioNotificaciones = RepositorioNotificaciones.getInstance();

  }

  @AfterEach
  public void tearDown() {
    heladera.getColaboradoresSuscriptos().clear();
    colaboradorHumano.getNotificaciones().clear();
  }

  @Test
  public void seAgregaCorrectamenteSuscriptorReporteIncidentes() {
    colaboradorHumano.suscribirseA(heladera, TipoSuscripcion.REPORTE_INCIDENTE, 0);
    Assertions.assertEquals(heladera.getColaboradoresSuscriptos().get(0), colaboradorHumano);
  }

  @Test
  public void seAgregaCorrectamenteSuscriptorLimiteViandas() {
    colaboradorHumano.suscribirseA(heladera, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 0 );
    Assertions.assertEquals(heladera.getColaboradoresSuscriptos().get(0), colaboradorHumano);
  }

  @Test
  public void seEliminaCorrectamenteSuscripcion() {
    colaboradorHumano.suscribirseA(heladera, TipoSuscripcion.REPORTE_INCIDENTE, 0);
    Suscripcion suscripcion = colaboradorHumano.getSuscripciones().get(0);
    colaboradorHumano.eliminarSuscripcionA(heladera, suscripcion);
    Assertions.assertTrue(heladera.getColaboradoresSuscriptos().isEmpty());
  }

  @Test
  public void seNotificaCorrectamenteASuscriptosNotificadorIncidentes() {
    colaboradorHumano.suscribirseA(heladera, TipoSuscripcion.REPORTE_INCIDENTE, 0);
    FallaTecnica fallaTecnica = new FallaTecnica();
    heladera.agregarIncidente(fallaTecnica);
    Assertions.assertFalse(colaboradorHumano.getNotificaciones().isEmpty());
  }

//  @Test
//  public void seNotificaCorrectamenteASuscriptosViandas() {
//    colaboradorHumano.suscribirseA(heladera, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 0);
//    Vianda vianda = mock(Vianda.class);
//    heladera.sacarVianda();
//    Assertions.assertFalse(colaboradorHumano.getNotificaciones().isEmpty());
//  }
}
*/

