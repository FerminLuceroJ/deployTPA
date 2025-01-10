package dominio.entidades.tecnicoHeladeras;

import dominio.entidades.heladera.Heladera;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SolicitudVisita {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_tecnico", referencedColumnName = "id")
  private TecnicoHeladera tecnico;
  @ManyToOne
  @JoinColumn(name = "id_heladera_origen", referencedColumnName = "id")
  private Heladera heladera;

  public SolicitudVisita() {}

  public SolicitudVisita(Heladera heladera, TecnicoHeladera tecnico) {
    this.heladera = heladera;
    this.tecnico = tecnico;
  }

  // Estos metodos hoy en dia no cambian en nada el comportamiento, asi que quedan en stand by
  public void aceptarVisita() {}

  public void rechazarVisita(){}
}
