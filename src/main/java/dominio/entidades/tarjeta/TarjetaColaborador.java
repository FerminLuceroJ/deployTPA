package dominio.entidades.tarjeta;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.aperturas.SolicitudApertura;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter @Setter
@Entity
public class TarjetaColaborador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String codigoAlfaNumerico;
  @Enumerated(EnumType.STRING)
  private EstadoTarjeta estadoTarjeta;
  /*@OneToMany
  @JoinColumn(name = "id_uso_tarjeta", referencedColumnName = "id")
  protected List<UsoDeTarjeta> usosDeTarjeta; */ //Ver que sentido tiene
  @OneToMany
  @JoinColumn(name = "id_solicitudApertura", referencedColumnName = "id")
  private List<SolicitudApertura> solicitudesPendientes;

  public TarjetaColaborador() {}

  public TarjetaColaborador(String codigoAlfanumerico) {this.codigoAlfaNumerico = codigoAlfanumerico;}

  public void tarjetaPerdida() {
    estadoTarjeta = EstadoTarjeta.PERDIDA;
  }

  public void tarjetaEntregada() {
    estadoTarjeta = EstadoTarjeta.ENTREGADA;
  }

  /*public void usarTarjeta(Heladera heladera) {
    usosDeTarjeta.add(new UsoDeTarjeta(heladera));
  } */ //Rever tema de usar tarjeta para un colaborador

}
  