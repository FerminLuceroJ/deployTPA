package dominio.entidades.formulario;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Pregunta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "pregunta")
  private String pregunta;
  @Column(name = "es_obligatoria")
  private Boolean esObligatoria;

  public Pregunta(String pregunta, Boolean esObligatoria) {
    this.pregunta = pregunta;
    this.esObligatoria = esObligatoria;
  }

  public Pregunta() {}

}