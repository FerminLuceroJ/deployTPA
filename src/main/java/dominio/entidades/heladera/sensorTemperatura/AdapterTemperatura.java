package dominio.entidades.heladera.sensorTemperatura;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sensorTemperatura.actions.Action;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class AdapterTemperatura implements TemperatureSensor {
  private TemperatureSensorTercero temperatureSensorTercero;

  // Constructor, getters y setters
  public AdapterTemperatura(TemperatureSensorTercero temperatureSensorTercero) {
    this.temperatureSensorTercero = temperatureSensorTercero;
  }

  public void connect(Heladera heladera) {
    temperatureSensorTercero.connect(heladera.getSerialNumber());
  }

  public void onTemperatureAction(Action action) {
    temperatureSensorTercero.onTemperatureAction(action);
  }

}
