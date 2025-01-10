package dominio.entidades.colaboracion;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.lectorArchivos.lecturaCSV.CargaCoeficientes;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter @Setter
@Entity
public class DonarDinero extends Colaboracion {

  @Column(name = "monto")
  private Integer monto;
  @Enumerated(EnumType.STRING)
  private Frecuencia frecuencia;

  public DonarDinero(Integer monto, Frecuencia frecuencia, Colaborador colaborador) {
    this.monto = monto;
    this.frecuencia = frecuencia;
    this.coeficiente = this.actualizarCoeficientes("archivos/CoeficientesColaboraciones.csv");
    this.fechaColaboracion = LocalDate.now();
    this.colaborador = colaborador;
  }

  public DonarDinero(LocalDate fechaDonacion, ColaboradorHumano colaboradorHumano) {
    this.fechaColaboracion = fechaDonacion;
    this.colaborador = colaboradorHumano;
  }

  public DonarDinero() {}

  @Override
  public void aplicarColaboracion() {
    // TODO
  }

  @Override
  public double calcularPuntaje() {
    return monto * coeficiente;
  }

  @Override
  public double actualizarCoeficientes(String path) {
    path = "archivos/CoeficientesColaboraciones.csv";
    CargaCoeficientes cargaCoeficientes = new CargaCoeficientes();
    cargaCoeficientes.cargarCoeficientes(path);

    return cargaCoeficientes.getCoeficiente("DINERO");
  }

}
