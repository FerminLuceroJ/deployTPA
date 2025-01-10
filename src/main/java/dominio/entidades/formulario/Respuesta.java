package dominio.entidades.formulario;

import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class Respuesta {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "contenido")
  private String contenido;
  @ManyToOne
  @JoinColumn(name = "id_pregunta", referencedColumnName = "id")
  private Pregunta pregunta;

  public Respuesta(String contenido) {
    this.contenido = contenido;
  }

  public Respuesta() {}
}
