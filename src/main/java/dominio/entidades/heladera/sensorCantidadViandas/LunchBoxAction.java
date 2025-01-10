package dominio.entidades.heladera.sensorCantidadViandas;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.NotificadorSuscriptos;

public class LunchBoxAction {
  private Heladera heladera;

  public LunchBoxAction(Heladera heladera){
    this.heladera = heladera;
  }

  public void onLunchBoxChanged(int newQuantity) {
    heladera.setCantidadViandas(newQuantity);
  }
}

