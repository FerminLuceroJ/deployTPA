package dominio.entidades.persona.colaborador;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.formulario.Formulario;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.aperturas.SolicitudApertura;
import dominio.entidades.heladera.incidentes.FallaTecnica;
import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaIncidente;
import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import dominio.entidades.tarjeta.solicitudes.SolicitudTarjeta;
import dominio.notificador.Notificacion;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioIncidente;
import dominio.repositorios.RepositorioSolicitudesTarjeta;
import java.util.ArrayList;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import dominio.repositorios.RepositoriosSolicitudAperturas;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Getter @Setter
@Entity
public class ColaboradorHumano extends Colaborador {

  @Column
  private String tipoDocumento;
  @Column
  private String numeroDocumento;
  @OneToOne
  @JoinColumn(name = "id_medioDeContacto", referencedColumnName = "id")
  private MedioContacto medioDeContactoFavorito;
  @OneToOne
  @JoinColumn(name = "id_tarjetaColaborador", referencedColumnName = "id")
  private TarjetaColaborador tarjetaColaborador;
  @OneToMany
  @JoinColumn(name = "id_tarjeta_pers_vulnerable", referencedColumnName = "id")
  private List<TarjetaPersonaVulnerable> tarjetasARepartir = new ArrayList<>();
  @OneToMany
  @JoinColumn(name = "id_solicitudTarjeta", referencedColumnName = "id")
  private List<SolicitudTarjeta> solicitudesTarjetas;
  @OneToMany
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private List<Notificacion> notificaciones = new ArrayList<>();


  public ColaboradorHumano(
      String direccion,
      String numeroDocumento,
      String tipoDocumento,
      String nombreYapellido,
      Formulario formulario,
      List<MedioContacto> mediosDeContacto,
      MedioContacto medioDeContactoFavorito
  ) {
    if (tipoDocumento == null) {
      throw new RuntimeException("Tipo de documento del colaborador no puede ser nulo");
    }
    if (numeroDocumento == null) {
      throw new RuntimeException("Numero de documento del colaborador no puede ser nulo");
    }

    this.direccion = direccion;
    this.nombreYapellido = nombreYapellido;
    this.numeroDocumento = numeroDocumento;
    this.tipoDocumento = tipoDocumento;
    this.mediosDeContacto = mediosDeContacto;
    this.medioDeContactoFavorito = medioDeContactoFavorito;
  }

  public ColaboradorHumano(){}

  @Override
  public String getTipo() {return "Humano";}

  @Override
  public void realizarColaboracion(Colaboracion colaboracion) {
    colaboracion.aplicarColaboracion();
    //colaboracionesRealizadas.add(colaboracion);
  }

  public void solicitarTarjeta(){
    RepositorioSolicitudesTarjeta repositorioSolicitudesTarjeta = RepositorioSolicitudesTarjeta.getInstance();
    repositorioSolicitudesTarjeta.persistir(new SolicitudTarjeta(this));
  }

  public void recibirTarjeta(TarjetaColaborador tarjeta) {
    tarjetaColaborador = tarjetaColaborador;
  }

  public void addTarjetaPersonaVulnerable(TarjetaPersonaVulnerable tarjetaPersonaVulnerable) {
    tarjetasARepartir.add(tarjetaPersonaVulnerable);
  }

  public void reportarFallaTecnica(Heladera heladera, String descripcion, String imagePath) {
    FallaTecnica fallaTecnica = new FallaTecnica(heladera, descripcion, imagePath);
    RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();
    repositorioIncidente.persistir(fallaTecnica);
    heladera.agregarIncidente(fallaTecnica);
  }

  /** Logica Tarjetas y Aperturas  */

  public void solicitarAperturaHeladera(Heladera heladera, Colaboracion colaboracion) {
    SolicitudApertura solicitudApertura = new SolicitudApertura(heladera, this, tarjetaColaborador, colaboracion);
    tarjetaColaborador.getSolicitudesPendientes().add(solicitudApertura);
    heladera.getAdapterControladorAcceso().notificarTarjetasColaboradorHabilitada(tarjetaColaborador);
    RepositoriosSolicitudAperturas.getInstance().persistir(solicitudApertura);
  }

  /** Logica solicitudes ante incidentes */

  public void addNotificacion(Notificacion notificacion) {
    notificaciones.add(notificacion);
  }

  public void aceptarSugerenciaDeHeladera(SugerenciaIncidente sugerenciaIncidente) {
    sugerenciaIncidente.aceptar(this);
  }
  
  @Override
  public Boolean esJuridico(){return false;}


}
