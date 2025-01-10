package dominio.entidades.heladera.sensorPeso;

import dominio.entidades.heladera.EstadoCapacidadHeladera;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdapterPeso implements SensorPeso {

  private String serialNumber;
  private SensorPesoTercero sensorPesoTercero;
  private Reading pesoYUnidad;

  public AdapterPeso(String serialNumber, SensorPesoTercero sensorPesoTercero){
    this.sensorPesoTercero = sensorPesoTercero;
    this.serialNumber = serialNumber;
  }

  public Reading obtenerReading(){
    return sensorPesoTercero.getWeight(serialNumber);
  }

  public double facilitarPeso(){
    if(this.obtenerReading().getValorpeso() < 0) throw new RuntimeException("Peso negativo");

    double valor = this.obtenerReading().getValorpeso();
    if(this.obtenerReading().getUnidad().equals("Libra")) return valor * 0.454;
    return valor;
  }

  public EstadoCapacidadHeladera estadoCapacidadHeladera(double capacidadHeladera){

    double pesoActual = this.facilitarPeso();
    double porcentaje = (pesoActual * 100) / capacidadHeladera;

    if (porcentaje <= 30) {
      return EstadoCapacidadHeladera.BAJO;
    } else if (porcentaje <= 70) {
      return EstadoCapacidadHeladera.MEDIO;
    } else {
      return EstadoCapacidadHeladera.ALTO;
    }
  }

}
