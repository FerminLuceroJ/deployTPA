package dominio.entidades.heladera.sensorTemperatura.actions;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.heladera.sensorTemperatura.Lectura;

public class ActionRutinaTemperatura implements Action {
  private Heladera heladera;

  public ActionRutinaTemperatura( Heladera heladera) {
    this.heladera = heladera;
  }

  public void executeForTemperature(Double temperature) {
    heladera.agregarYEvaluarLectura(temperature);
    heladera.evaluarUltimas3Lecturas();
  }
}
