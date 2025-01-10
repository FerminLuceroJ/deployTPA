package dominio.entidades.colaboracion;


import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.vianda.Estado;
import dominio.entidades.vianda.Vianda;
import dominio.lectorArchivos.lecturaCSV.CargaCoeficientes;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
@Entity
public class DonarVianda extends Colaboracion {

  @OneToOne
  @JoinColumn(name = "id_vianda", referencedColumnName = "id")
  private Vianda vianda;
  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;

  public DonarVianda(Vianda vianda, Heladera heladera, Colaborador colaboradorHumano) {
    this.fechaColaboracion = LocalDate.now();
    this.vianda = vianda;
    this.coeficiente = this.actualizarCoeficientes("archivos/CoeficientesColaboraciones.csv");
    this.heladera = heladera;
    this.colaborador = colaboradorHumano;
  }

  public DonarVianda() {}

  public DonarVianda(LocalDate fechaColaboracion, ColaboradorHumano colaboradorHumano) {
    this.fechaColaboracion = fechaColaboracion;
    this.colaborador = colaboradorHumano;
  }

  public Integer semanasFrescas() {

    long diasEntre = ChronoUnit.DAYS.between(LocalDate.now(), vianda.getFechaCaducidad());

    return (int) Math.ceil(diasEntre / 7.0);
  }

  @Override
  public void aplicarColaboracion() {
    vianda.setEstado(Estado.ENTREGADA);
    heladera.addVianda(vianda);
  }

  @Override
  public double calcularPuntaje() {
    return this.semanasFrescas() * coeficiente;
  }

  @Override
  public double actualizarCoeficientes(String path) {
    path = "archivos/CoeficientesColaboraciones.csv";
    CargaCoeficientes cargaCoeficientes = new CargaCoeficientes();
    cargaCoeficientes.cargarCoeficientes(path);

    return cargaCoeficientes.getCoeficiente("DONACION_VIANDAS");
  }

  /*public void setColaborador(ColaboradorHumano colaborador) {
    this.colaborador = colaborador;
  }*/

}
