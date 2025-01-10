package dominio.entidades.heladera.aperturas;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.tarjeta.TarjetaColaborador;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class SolicitudApertura {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Boolean aperturaRealizada = false;
  @Column
  private LocalDateTime horarioApertura;
  @Column
  private LocalDateTime horarioPermisoDado;
  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;
  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private ColaboradorHumano colaboradorHumano;
  @OneToOne
  @JoinColumn(name = "id_colaboracion", referencedColumnName = "id")
  private Colaboracion colaboracion;

  public SolicitudApertura() {}

  public SolicitudApertura(Heladera heladera, ColaboradorHumano colaboradorHumano, TarjetaColaborador tarjetaColaborador, Colaboracion colaboracion) {
    this.heladera = heladera;
    this.colaboradorHumano = colaboradorHumano;
    this.colaboracion = colaboracion;
    this.horarioPermisoDado = LocalDateTime.now();
  }

  public void aplicarSolicitud() {
    colaboradorHumano.realizarColaboracion(colaboracion);
    horarioApertura = LocalDateTime.now();
    aperturaRealizada = true;
  }

}
