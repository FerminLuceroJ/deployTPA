package dominio.entidades.heladera.sensorPeso;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Reading {

  private double valorpeso;
  private String unidad;

  public Reading(double valorpeso, String unidad) {
    this.valorpeso = valorpeso;
    this.unidad = unidad;
  }

  public String getPeso(){
    return ("El peso es" + valorpeso + unidad);
  }
}
