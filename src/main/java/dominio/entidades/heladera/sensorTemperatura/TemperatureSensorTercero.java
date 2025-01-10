package dominio.entidades.heladera.sensorTemperatura;

import dominio.entidades.heladera.sensorTemperatura.actions.Action;

public interface TemperatureSensorTercero {

  void connect(String serialNumber);
  void onTemperatureAction(Action action);

}
