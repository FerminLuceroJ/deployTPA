package dominio.entidades.heladera.sensorTemperatura;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Lectura {

  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "horario_lectura")
  private LocalDateTime horarioLectura = LocalDateTime.now();
  @Column(name = "temperatura")
  private Double temperatura;

  public Lectura() {}

  public Lectura(Double temperatura) {
    this.temperatura = temperatura;
  }

}
