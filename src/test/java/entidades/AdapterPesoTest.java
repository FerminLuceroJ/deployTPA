package entidades;

import dominio.entidades.heladera.EstadoCapacidadHeladera;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorPeso.Reading;
import dominio.entidades.heladera.sensorPeso.SensorPesoTercero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdapterPesoTest {

  private AdapterPeso sensorPeso;
  private SensorPesoTercero sensorPesoTercero;


  @BeforeEach
  public void setUp() {
    sensorPesoTercero = mock(SensorPesoTercero.class);
    when(sensorPesoTercero.getWeight("sarasa123")).thenReturn(new Reading(100.0, "kg"));
    sensorPeso = new AdapterPeso("sarasa123", sensorPesoTercero);
    sensorPeso.setSensorPesoTercero(sensorPesoTercero);
  }



  @Test
  public void seObtieneCorrectamenteElPesajeDeHeladera(){
    sensorPeso.obtenerReading();

    Assertions.assertEquals(sensorPeso.facilitarPeso(), 100.0);
  }

  @Test
  public void seDetectaNivelBajoDeLlenado(){

    sensorPeso.obtenerReading();
    //Se asume capacidad de heladera 4000kg
    double capacidadHeladera = 4000.0;

    Assertions.assertEquals(sensorPeso.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.BAJO);
  }

  @Test
  public void seDetectaNivelMedioDeLlenado(){
    //Se asume capacidad heladera 200kg
    double capacidadHeladera = 200.0;
    sensorPeso.obtenerReading();

    Assertions.assertEquals(sensorPeso.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.MEDIO);
  }

  @Test
  public void seDetectaNivelAltoDeLlenado(){
    //Se asume capacidad heladera 120kg
    double capacidadHeladera = 120.0;

    sensorPeso.obtenerReading();

    Assertions.assertEquals(sensorPeso.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.ALTO);
  }

}
