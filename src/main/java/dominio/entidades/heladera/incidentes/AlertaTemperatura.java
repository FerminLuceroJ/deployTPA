package dominio.entidades.heladera.incidentes;

import dominio.entidades.heladera.Heladera;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Temperatura")
public class AlertaTemperatura extends Incidente {

  @Column(nullable = true)
  private Double temperatura;

  public AlertaTemperatura() {}

  public AlertaTemperatura(Heladera heladera, Double temperatura) {
    super(heladera);
    this.temperatura = temperatura;
  }
}
