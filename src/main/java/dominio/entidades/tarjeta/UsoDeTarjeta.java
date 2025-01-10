package dominio.entidades.tarjeta;

import dominio.entidades.heladera.Heladera;
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

@Getter @Setter
@Entity
public class UsoDeTarjeta {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;
  @Column(name = "fecha_uso")
  private LocalDate fechaDeUso;

  public UsoDeTarjeta(Heladera heladera){
    if(heladera == null){
      throw new RuntimeException("Indicar la heladera a intervenir");
    }
    this.heladera = heladera;
    this.fechaDeUso = LocalDate.now();
  }

 public UsoDeTarjeta() {}
}
