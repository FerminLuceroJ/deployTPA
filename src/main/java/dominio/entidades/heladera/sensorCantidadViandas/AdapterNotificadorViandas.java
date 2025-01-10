package dominio.entidades.heladera.sensorCantidadViandas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdapterNotificadorViandas {
  private CantidadViandasSensorTercero sensorCantidadViandasTercero;

  public AdapterNotificadorViandas(CantidadViandasSensorTercero sensorCantidadViandasTercero, LunchBoxAction lunchBoxAction) {
    this.sensorCantidadViandasTercero = sensorCantidadViandasTercero;
  }

  public void onLunchBoxChanged(LunchBoxAction lunchBoxAction){
    sensorCantidadViandasTercero.onLunchBoxChanged(lunchBoxAction);
  }
}
