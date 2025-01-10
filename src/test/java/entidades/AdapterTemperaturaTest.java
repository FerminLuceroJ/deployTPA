/*  package entidades;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.heladera.sensorTemperatura.Lectura;
import dominio.entidades.heladera.sensorTemperatura.TemperatureSensorTercero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class AdapterTemperaturaTest {

  private AdapterTemperatura adapterTemperatura;
  private TemperatureSensorTercero temperatureSensorTercero;
  private Heladera heladera;


  @BeforeEach
  public void setUp() {
    heladera = mock(Heladera.class);
    temperatureSensorTercero = mock(TemperatureSensorTercero.class);
    adapterTemperatura = new AdapterTemperatura(temperatureSensorTercero);
  }

  @Test
  public void seAgreganLecturasCorrectamente() {
    adapterTemperatura.configurarTemperaturas(0, 10);

    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);

    Assertions.assertEquals(adapterTemperatura.getLecturas().size(), 3);
  }

  @Test
  public void seAtiendeHeladeraAnteLecturasFueraDelMargen() {

    adapterTemperatura.configurarTemperaturas(4.0, 8.0);

    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);

    adapterTemperatura.evaluarUltimas3Lecturas();


    Assertions.assertTrue(adapterTemperatura.isNecesitaAtencion());
  }

  @Test
  public void noSeAtiendeHeladeraAnteLecturasDentroDelMargen() {

    adapterTemperatura.configurarTemperaturas(4.0, 8.0);

    adapterTemperatura.recibirYevaluarLectura(new Lectura(5.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(5.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(5.0), heladera);

    adapterTemperatura.evaluarUltimas3Lecturas();

    Assertions.assertFalse(adapterTemperatura.isNecesitaAtencion());
  }

  @Test
  public void noSeAtiendeHeladeraSiNoHayMasDeDosLecturas() {
    adapterTemperatura.configurarTemperaturas(0, 10);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.recibirYevaluarLectura(new Lectura(1.0), heladera);
    adapterTemperatura.evaluarUltimas3Lecturas();

    Assertions.assertFalse(adapterTemperatura.isNecesitaAtencion());
  }

}
*/