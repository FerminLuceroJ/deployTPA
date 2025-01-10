package dominio.entidades.formulario;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
public class Formulario {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fecha_emision")
  private LocalDate fechaDeEmision;
  @ManyToMany
  @JoinTable(
      name = "formulario_pregunta",
      joinColumns = @JoinColumn(name = "formulario_id"),
      inverseJoinColumns = @JoinColumn(name = "pregunta_id")
  )
  private List<Pregunta> preguntas;

  // TODO private RepositorioPreguntas repositorioPreguntas = RepositorioPreguntas.getInstancia();

  public Formulario(List<Pregunta> preguntas) {
    this.fechaDeEmision = LocalDate.now();
    this.preguntas = preguntas;
  }

  public Formulario() {}

  public void agregarPregunta(Pregunta pregunta) {
    preguntas.add(pregunta);
  }

  public void sacarPreguntar(Pregunta pregunta) {
    preguntas.remove(pregunta);
  }

}



