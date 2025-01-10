package dominio.reportes;

import dominio.entidades.persona.colaborador.Colaborador;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Reporte {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private Colaborador colaborador;
  @Column(name = "fecha_reporte")
  private LocalDate fechaReporte;
  @Column
  private double puntaje;

  public Reporte(){};

  public Reporte(
      Colaborador colaborador,
      LocalDate fechaReporte,
      double puntaje
  ) {
    this.colaborador = colaborador;
    this.fechaReporte = fechaReporte;
    this.puntaje = puntaje;
  }

}
