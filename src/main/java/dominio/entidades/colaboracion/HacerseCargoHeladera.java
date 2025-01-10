package dominio.entidades.colaboracion;

import dominio.entidades.heladera.Heladera;
import dominio.lectorArchivos.lecturaCSV.CargaCoeficientes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter @Setter
@Entity
public class HacerseCargoHeladera extends Colaboracion {

  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;

  public HacerseCargoHeladera(Heladera heladera) {
    this.fechaColaboracion = LocalDate.now();
    this.heladera = heladera;
    this.coeficiente = this.actualizarCoeficientes("archivos/CoeficientesColaboraciones.csv");
  }

  public HacerseCargoHeladera() {}

  @Override
  public void aplicarColaboracion() {
    // TODO
  }

  public Integer mesesActiva() {
    return heladera.mesesActiva();
  }

  public Integer cantidadUsos() {
    return heladera.getCantidadUsos();
  }

  @Override
  public double calcularPuntaje() {
    double puntaje = (this.mesesActiva() * this.cantidadUsos() * coeficiente);
    return BigDecimal.valueOf(puntaje).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  @Override
  public double actualizarCoeficientes(String path) {
    path = "archivos/CoeficientesColaboraciones.csv";
    CargaCoeficientes cargaCoeficientes = new CargaCoeficientes();
    cargaCoeficientes.cargarCoeficientes(path);

    return cargaCoeficientes.getCoeficiente("QUEDARSE_HELADERA");
  }
}
