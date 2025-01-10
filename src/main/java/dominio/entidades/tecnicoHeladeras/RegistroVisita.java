package dominio.entidades.tecnicoHeladeras;

import dominio.entidades.heladera.Heladera;
import dominio.imageLoader.ImageLoader;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Getter @Setter
public class RegistroVisita {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_heladera_origen", referencedColumnName = "id")
  private Heladera heladera;
  @ManyToOne
  @JoinColumn(name = "id_tecnico", referencedColumnName = "id")
  private TecnicoHeladera tecnicoHeladera;
  @Column
  private String descripcion;
  @Column
  private boolean pudoSolucionarProblema;
  @Column
  private LocalDate fecha = LocalDate.now();
  @Transient
  private ImageLoader imageLoader;

  public RegistroVisita() {}

  public RegistroVisita(Heladera heladera, TecnicoHeladera tecnicoHeladera,  String descripcion,
                        String imagePath, boolean pudoSolucionarProblema) {
    this.heladera = heladera;
    this.tecnicoHeladera = tecnicoHeladera;
    this.descripcion = descripcion;
    this.pudoSolucionarProblema = pudoSolucionarProblema;
    //imageLoader = new ImageLoader(imagePath);
  }


}
