package dominio.entidades.persona.colaborador;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.formulario.Formulario;
import dominio.entidades.formulario.Respuesta;
import dominio.localizacion.Localizacion;
import dominio.notificador.mediosDeContacto.MedioContacto;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class  Colaborador {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombreYapellido")
  protected String nombreYapellido;
  @Column(name = "razonSocial")
  protected String razonSocial;
  @Column(name = "direccion")
  protected String direccion;
  @OneToMany
  List<Respuesta> respuestasAFormulario;
  @OneToMany
  @JoinColumn(name = "id_medio_contacto", referencedColumnName = "id")
  protected List<MedioContacto> mediosDeContacto;
  @Embedded
  protected Localizacion localizacion;

  abstract void realizarColaboracion(Colaboracion colaboracion);
  abstract String getTipo();
  abstract Boolean esJuridico();

  /**       CORRECCION INDICADA POR GASTON
   *  Vamos a meter lista de respuestas, no nos interesa que el colaborador tenga un formulario
   El formulario solamente queda como paso previo para saber de donde sacar las preguntas a hacerle al colaborador
   */

}
