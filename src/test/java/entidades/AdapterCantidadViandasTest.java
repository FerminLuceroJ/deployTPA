/* import entidades.heladera.EstadoCapacidadHeladera;
import entidades.heladera.sensorCantidadViandas.AdapterNotificadorViandas;
import entidades.heladera.sensorCantidadViandas.SensorCantidadViandas;
import entidades.heladera.sensorPeso.Reading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdapterCantidadViandasTest {

  private AdapterNotificadorViandas adapterCantidadViandas;
  private SensorCantidadViandas sensorCantidadViandas;


  @BeforeEach
  public void setUp() {
    sensorCantidadViandas = mock(SensorCantidadViandas.class);
    when(sensorCantidadViandas.getWeight("sarasa123")).thenReturn(new Reading(100.0, "kg"));
    adapterCantidadViandas = new AdapterNotificadorViandas();
    adapterCantidadViandas.setSensorPesoTercero(sensorCantidadViandas);
  }



  @Test
  public void seObtieneCorrectamenteElPesajeDeHeladera(){
    adapterCantidadViandas.obtenerReading();

    Assertions.assertEquals(adapterCantidadViandas.facilitarPeso(), 100.0);
  }

  @Test
  public void seDetectaNivelBajoDeLlenado(){

    adapterCantidadViandas.obtenerReading();
    //Se asume capacidad de heladera 4000kg
    double capacidadHeladera = 4000.0;

    Assertions.assertEquals(adapterCantidadViandas.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.BAJO);
  }

  @Test
  public void seDetectaNivelMedioDeLlenado(){
    //Se asume capacidad heladera 200kg
    double capacidadHeladera = 200.0;
    adapterCantidadViandas.obtenerReading();

    Assertions.assertEquals(adapterCantidadViandas.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.MEDIO);
  }

  @Test
  public void seDetectaNivelAltoDeLlenado(){
    //Se asume capacidad heladera 120kg
    double capacidadHeladera = 120.0;

    adapterCantidadViandas.obtenerReading();

    Assertions.assertEquals(adapterCantidadViandas.estadoCapacidadHeladera(capacidadHeladera), EstadoCapacidadHeladera.ALTO);
  }

} */
