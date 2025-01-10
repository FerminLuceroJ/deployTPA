package dominio.notificador;

import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Notificacion {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private ColaboradorHumano colaborador;
  @Column
  private String mensaje;
  @Column
  private String asunto;
  @Enumerated(EnumType.STRING)
  private TipoNotificacion tipoNotificacion;
  @Column(name = "fecha_notificacion")
  private LocalDate fechaNotificacion;
  @OneToOne
  @JoinColumn(name = "id_sugerencia", referencedColumnName = "id")
  private SugerenciaMoverViandas sugerencia;


  public Notificacion() {}

  public Notificacion(ColaboradorHumano colaborador, String mensaje, String asunto, SugerenciaMoverViandas sugerencia) {
    this.colaborador = colaborador;
    this.mensaje = mensaje;
    this.asunto = asunto;
    this.sugerencia = sugerencia;
    this.setFechaNotificacion(LocalDate.now());
  }
}
