package dominio.entidades.tecnicoHeladeras;

import dominio.entidades.heladera.Heladera;
import dominio.localizacion.Localizacion;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import dominio.repositorios.RepositorioRegistroVisita;
import lombok.Getter;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioRegistroVisita;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TecnicoHeladera {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String nombre;
  @Column
  private String apellido;
  @Column
  private String direccion;
  @Transient
  private List<MedioContacto> medioContactos;
  @Getter
  @Embedded
  private Localizacion localizacion;
  @Transient
  private RepositorioRegistroVisita repositorioVisitasHeladera;
  @Getter
  @OneToMany
  @JoinColumn(name = "solicitudes_visita")
  private List<SolicitudVisita> solicitudesVisitas = new ArrayList<>();

  public TecnicoHeladera() {}

  public TecnicoHeladera(String nombre, String direccion, List<MedioContacto> mediosDeContacto, Localizacion localizacion, List<SolicitudVisita> solicitudesVisitas) {
    this.nombre = nombre;
    this.direccion = direccion;
    this.medioContactos = mediosDeContacto;
    this.localizacion = localizacion;
    this.solicitudesVisitas = solicitudesVisitas;

  }

  public void generarReporteVisita(Heladera heladera, String descripcion, String imagePath, boolean pudoSolucionar) {
    heladera.setEstaActiva(pudoSolucionar);
    repositorioVisitasHeladera.persistir(new RegistroVisita(heladera, this, descripcion,
        imagePath, pudoSolucionar));
  }

  public void agregarSolicitudVisita(SolicitudVisita solicitudVisita) {
    solicitudesVisitas.add(solicitudVisita);
  }

}