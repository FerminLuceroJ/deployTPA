package dominio.entidades.tarjeta.solicitudes;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.tarjeta.TarjetaColaborador;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SolicitudTarjeta implements Solicitud{

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private ColaboradorHumano colaborador;

  //private RepositorioMovimientos repositorioMovimientos = RepositorioMovimientos.getIntancia();


  public SolicitudTarjeta(ColaboradorHumano colaborador){
    this.colaborador = colaborador;
  }

  public SolicitudTarjeta() {}

  public void aceptarSolicitud(TarjetaColaborador tarjetaColaborador) {
    colaborador.recibirTarjeta(tarjetaColaborador);
  }
}
