package dominio.entidades.heladera.incidentes;

import dominio.entidades.heladera.Heladera;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dominio.entidades.tecnicoHeladeras.ManagerVisitas;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Incidente {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;
  @Column(name = "fecha_hora")
  private LocalDate fechayHora = LocalDate.now();
  @Getter
  @Lob
  @Column(nullable = true)
  private byte[] imageData;
  public Incidente(Heladera heladera) {
    this.heladera = heladera;
  }

  public Incidente() {}

  public String getTipoIncidente() {
    DiscriminatorValue discriminatorValue = this.getClass().getAnnotation(DiscriminatorValue.class);
    return discriminatorValue != null ? discriminatorValue.value() : "Desconocido";
  }

  public void avisarTecnico() {
    ManagerVisitas managerVisitas = ManagerVisitas.getInstancia();
    managerVisitas.notificarTecnico(heladera);
  }

  public String getFechayHoraFormateada() {
    if (fechayHora == null) {
      return "Fecha no disponible";
    }
    return fechayHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }


}
