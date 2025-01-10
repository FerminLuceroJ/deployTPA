package dominio.entidades.heladera.incidentes;

import dominio.entidades.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@DiscriminatorValue("Conexion")
public class AlertaConexion extends Incidente {

  @Column(nullable = true)
  @Enumerated(EnumType.STRING)
  private TipoFallaConexion tipoFallaConexion;

  public AlertaConexion() {}

  public AlertaConexion(Heladera heladera, TipoFallaConexion tipoFallaConexion) {
    super(heladera);
    this.tipoFallaConexion = tipoFallaConexion;
  }

  public String tipoConexion() {
    if(tipoFallaConexion == TipoFallaConexion.FALLA_EN_TEMPERATURA) {
      return "Temperatura";
    } else return "Peso";
  }
}
