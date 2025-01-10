package dominio.notificador.mediosDeContacto;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.notificador.Notificacion;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class MedioContacto {

  @Id @GeneratedValue
  private Long id;
  @Column(name = "contenido")
  protected String contenido;

  abstract public void notificar(Notificacion notificacion, ColaboradorHumano colaboradorHumano);
}
