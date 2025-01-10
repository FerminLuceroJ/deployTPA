package dominio.entidades.colaboracion;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.vianda.Vianda;
import dominio.lectorArchivos.lecturaCSV.CargaCoeficientes;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class DistribuirVianda extends Colaboracion {

  @Column(name = "cantidad_viandas")
  private Integer cantidadViandas;
  @Column(name = "motivo")
  private String motivo;
  @ManyToOne
  @JoinColumn(name = "id_heladera_origen", referencedColumnName = "id")
  private Heladera heladeraOrigen;
  @ManyToOne
  @JoinColumn(name = "id_heladera_destino", referencedColumnName = "id")
  private Heladera heladeraDestino;

  public DistribuirVianda(){}

  public DistribuirVianda(
      Integer cantidadViandas,
      String motivo,
      Heladera heladeraOrigen,
      Heladera heladeraDestino
  ) {
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.cantidadViandas = cantidadViandas;
    this.motivo = motivo;
    this.fechaColaboracion = LocalDate.now();
    this.coeficiente = this.actualizarCoeficientes("archivos/CoeficientesColaboraciones.csv");
  }

  public DistribuirVianda(LocalDate fechaDonacion, ColaboradorHumano colaboradorHumano) {
    this.fechaColaboracion = fechaDonacion;
    this.colaborador = colaboradorHumano;
  }

  @Override
  public void aplicarColaboracion() {
    List<Vianda> viandasRetiradas = new ArrayList<Vianda>();
    viandasRetiradas = heladeraOrigen.sacarViandas(cantidadViandas);
    heladeraDestino.agregarViandas(viandasRetiradas);

    for(Vianda vianda : viandasRetiradas){
      vianda.setHeladeraUbicada(heladeraDestino);

    }
  }

  @Override
  public double calcularPuntaje() {
    return cantidadViandas * coeficiente;
  }

  @Override
  public double actualizarCoeficientes(String path) {
    path = "archivos/CoeficientesColaboraciones.csv";
    CargaCoeficientes cargaCoeficientes = new CargaCoeficientes();
    cargaCoeficientes.cargarCoeficientes(path);

    return cargaCoeficientes.getCoeficiente("REDISTRIBUCION_VIANDAS");
  }

}
