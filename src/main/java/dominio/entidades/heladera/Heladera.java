package dominio.entidades.heladera;


import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.incidentes.suscripciones.TipoSuscripcion;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.aperturas.AdapterControladorAcceso;
import dominio.entidades.heladera.aperturas.SolicitudApertura;
import dominio.entidades.heladera.incidentes.AlertaConexion;
import dominio.entidades.heladera.incidentes.AlertaTemperatura;
import dominio.entidades.heladera.incidentes.Incidente;
import dominio.entidades.heladera.incidentes.NotificadorSuscriptos;
import dominio.entidades.heladera.incidentes.TipoFallaConexion;
import dominio.entidades.heladera.sensorCantidadViandas.AdapterNotificadorViandas;
import dominio.entidades.heladera.sensorCantidadViandas.LunchBoxAction;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.heladera.sensorTemperatura.Lectura;
import dominio.entidades.heladera.sensorTemperatura.actions.ActionRutinaTemperatura;
import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.vianda.Vianda;
import dominio.localizacion.Localizacion;
import dominio.repositorios.RepositorioIncidente;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Heladera {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "serialNumber")
  private String serialNumber;
  @Column(name = "nombreSignificativo")
  private String nombreSignificativo;
  @Column(name = "direccion")
  private String direccion;
  @Column(name = "capacidadDeViandas")
  private Integer capacidadDeViandas;
  @Column(name = "fechaFuncionamiento")
  private LocalDate fechaFuncionamiento;
  @Column(name = "cantidadDeUsos")
  private Integer cantidadUsos;
  @Column(name = "estaActiva")
  private Boolean estaActiva;
  @Column(name = "temperaturaMinima")
  private double temperaturaMinima;
  @Column(name = "temperaturaMaxima")
  private double temperaturaMaxima;
  @Column
  private boolean necesitaAtencion;
  @Enumerated(EnumType.STRING)
  private EstadoCapacidadHeladera estadoCapacidadHeladera;
  @Embedded
  private Localizacion localizacion;
  @OneToMany(mappedBy = "heladeraUbicada", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Vianda> viandas = new ArrayList<>();
  @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Incidente> fallasHeladera = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Lectura> lecturasTemperatura = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private List<Suscripcion> suscripcions = new ArrayList<>();

  /** Conviene hacer que heladera tenga la lista de Suscripciones (LO DIJO GASTON) */

  @Transient
  private LunchBoxAction lunchBoxAction;
  @Transient
  private AdapterTemperatura adapterTemperatura;
  @Transient
  private AdapterPeso adapterPeso;
  @Transient
  private AdapterControladorAcceso adapterControladorAcceso;
  @Transient
  private AdapterNotificadorViandas adapterNotificadorViandas;
  @Transient
  private ActionRutinaTemperatura actionRutinaTemperatura;


  public Heladera(
      String serialNumber,
      String nombreSignificativo,
      Localizacion localizacion,
      String direccion,
      Integer capacidadDeViandas,
      LocalDate fechaFuncionamiento,
      Boolean estaActiva,
      List<Vianda> viandas,
      double temperaturaMinima,
      double temperaturaMaxima,
      AdapterTemperatura adapterTemperatura,
      AdapterPeso adapterPeso,
      AdapterControladorAcceso adapterControladorAcceso,
      AdapterNotificadorViandas adapterNotificadorViandas)
  {
    if (nombreSignificativo == null || nombreSignificativo.isEmpty()) {
      throw new RuntimeException("Indicar el nombre significativo");
    }

    if (localizacion == null) {
      throw new RuntimeException("Indicar el punto estrategico");
    }

    if (direccion == null || direccion.isEmpty()) {
      throw new RuntimeException("Indicar la direccion");
    }

    if (capacidadDeViandas == null) {
      throw new RuntimeException("Indicar la capacidad de viandas");
    }

    if (fechaFuncionamiento == null) {
      throw new RuntimeException("Indicar la fecha funcionamiento");
    }

    this.serialNumber = serialNumber;
    this.nombreSignificativo = nombreSignificativo;
    this.localizacion = localizacion;
    this.direccion = direccion;
    this.capacidadDeViandas = capacidadDeViandas;
    this.fechaFuncionamiento = fechaFuncionamiento;
    this.estaActiva = estaActiva;
    this.viandas = viandas;
    this.cantidadUsos = 0;
    this.temperaturaMinima = temperaturaMinima;
    this.temperaturaMaxima = temperaturaMaxima;
    this.adapterPeso = adapterPeso;
    this.adapterControladorAcceso = adapterControladorAcceso;
    this.adapterTemperatura = adapterTemperatura;
    this.lunchBoxAction = new LunchBoxAction(this);
    this.adapterNotificadorViandas = adapterNotificadorViandas;
    this.actionRutinaTemperatura = new ActionRutinaTemperatura(this);

  }

  public Heladera() {

  }


  public void addVianda(Vianda vianda) {
    if (vianda == null) {
      throw new RuntimeException("La vianda es requerida");
    }
    viandas.add(vianda);
  }

  public void sacarVianda() {
    viandas.remove(1);
    this.notificarCantidadViandas();
  }

  public List<Vianda> sacarViandas(Integer cantidadViandas) {
    List<Vianda> viandasSacadas = new ArrayList<Vianda>();
    for (int i = 0; i < cantidadViandas; i++) {
      Vianda vianda = this.viandas.get(i);
      viandasSacadas.add(vianda);
    }
    this.notificarCantidadViandas();
    return viandasSacadas;

  }

  public void agregarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
    this.notificarCantidadViandas();
  }

  public void setCantidadViandas(int newQuantity) {
    viandas.subList(0, newQuantity).clear();
    this.notificarCantidadViandas();
  }

  public void ejecutarRutinaAnteCambioDeVianda() {
    adapterNotificadorViandas.onLunchBoxChanged(lunchBoxAction);
  }


  public Integer capacidadRestante(){
    return capacidadDeViandas - cantidadDeViandas();
  }

  @Override
  public String toString() {
    return "Heladera: "
        + "puntoEstrategico=" + localizacion
        + ", nombreSignificativo=" + nombreSignificativo;
  }

  public Boolean estaActiva() {
    return estaActiva;
  }

  /** Logica de Tarjetas */

  public void abrirHeladera(TarjetaColaborador tarjetaColaborador) {
    List<SolicitudApertura> solicitudesAperturaPendientes = tarjetaColaborador.getSolicitudesPendientes();
    SolicitudApertura solicitudAperturaPendiente = solicitudesAperturaPendientes.stream()
    .filter(solicitud -> !solicitud.getAperturaRealizada()&& solicitud.getHeladera().equals(this)).findFirst()
    .orElseThrow(() -> new RuntimeException("No hay solicitudes pendientes"));
    solicitudAperturaPendiente.aplicarSolicitud();
    //tarjetaColaborador.usarTarjeta(this);
  }

  /** Logica de Peso */

  public double calculoCapacidad() {
    return capacidadDeViandas * 0.4;
  }

  public Integer cantidadDeViandas() {
    return viandas.size();
  }

  public Integer mesesActiva() {
    long diasEntre = ChronoUnit.DAYS.between(fechaFuncionamiento, LocalDate.now());

    return  (int) Math.ceil(diasEntre / 31.0);
  }

  public EstadoCapacidadHeladera estadoCapacidadHeladera() {
    return adapterPeso.estadoCapacidadHeladera(this.calculoCapacidad());
  }

  /** Logica Incidentes */

  public void alertarTemperatura(double temperatura) {
    Incidente incidente = new AlertaTemperatura(this, temperatura);
    RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();
    repositorioIncidente.persistir(incidente);
    this.agregarIncidente(incidente);
  }

  public Double calcularPeso() {
    Double peso = viandas.stream().mapToDouble(Vianda::getPeso).sum();
    return BigDecimal.valueOf(peso).setScale(2, RoundingMode.HALF_UP).doubleValue();
  }

  public Double getPeso() {
    try {
      return adapterPeso.facilitarPeso();
    } catch(Exception e) {
      this.alertarFallaConexionDeTipo(TipoFallaConexion.FALLA_EN_PESO);
      return null;
    }
  }

  public void diagnosticarUltimaLeturaHaceMasDe15Mins() {
    if(this.ultimaLecturaHaceMasDe15Mins()) {
      this.alertarFallaConexionDeTipo(TipoFallaConexion.FALLA_EN_TEMPERATURA);
    }
  }

  public void alertarFallaConexionDeTipo(TipoFallaConexion tipoFallaConexion) {
    Incidente incidente = new AlertaConexion(this, tipoFallaConexion);
    RepositorioIncidente repositorioIncidente = RepositorioIncidente.getInstance();
    repositorioIncidente.persistir(incidente);
    this.desactivarHeladera();
    this.agregarIncidente(incidente);
  }

  public void agregarIncidente(Incidente incidente) {
    fallasHeladera.add(incidente);
    this.notificarIncidente();
    incidente.avisarTecnico();
  }

  public void desactivarHeladera() {
    estaActiva = false;
  }


  /** Logica Temperatura */

  public Lectura getUltimaLectura() {
    return lecturasTemperatura.get(lecturasTemperatura.size() - 1);
  }

  public Double temperaturaActual(){
    return getUltimaLectura().getTemperatura();
  }

  public void agregarYEvaluarLectura(double temperatura) {
    Lectura lectura = new Lectura(temperatura);
    lecturasTemperatura.add(lectura);
    this.diagnosticarAlertaLecturaFueraDeRango(lectura);
  }

  public void evaluarUltimas3Lecturas() {

    List<Lectura> ultimasTresLecturas;

    int size = lecturasTemperatura.size();
    if (size < 3) {
      necesitaAtencion = false;
    } else {
      ultimasTresLecturas = lecturasTemperatura;
      necesitaAtencion = ultimasTresLecturas.stream().allMatch(lectura -> lectura.getTemperatura() < temperaturaMinima);
    }
  }

  public Boolean ultimaLecturaHaceMasDe15Mins() {
    LocalDateTime horarioActual = LocalDateTime.now();
    LocalDateTime horarioUltimaLectura = this.getUltimaLectura().getHorarioLectura();
    return horarioUltimaLectura.plusMinutes(15).isBefore(horarioActual);
  }

  public void diagnosticarAlertaLecturaFueraDeRango(Lectura lectura) {
    Double temperatura = lectura.getTemperatura();
    if(temperatura < temperaturaMinima || temperatura > temperaturaMaxima) {
      this.alertarTemperatura(lectura.getTemperatura());
    }
  }

  public void ejecutarRutina() {
    adapterTemperatura.onTemperatureAction(actionRutinaTemperatura);
  }

  /** Logica suscripciones */

  public void addColaboradorSuscripto(Suscripcion suscripcion) {
    suscripcions.add(suscripcion);
  }

  public void removeSuscripcion(Suscripcion suscripcion) {
    if(!suscripcions.contains(suscripcion)) {
      throw new RuntimeException("No existe la suscripcion");
    } else {
      suscripcions.remove(suscripcion);
    }
  }
  
  public List<Suscripcion> buscarSuscripcion(ColaboradorHumano colaborador) {
    return suscripcions.stream()
        .filter(suscripcion -> suscripcion.getColaboradorHumano().equals(colaborador)).
        toList();
  }

  public void notificarCantidadViandas() {
    suscripcions.stream().filter(suscripcion -> suscripcion.getTipoSuscripcion()==TipoSuscripcion.CANTIDAD_DE_VIANDAS).
        forEach(suscripcion -> suscripcion.notificar(this));
  }

  public void notificarIncidente() {
    suscripcions.stream().filter(suscripcion -> suscripcion.getTipoSuscripcion()==TipoSuscripcion.REPORTE_INCIDENTE).
        forEach(suscripcion -> suscripcion.notificar(this));
  }

  public boolean estaSuscripto(ColaboradorHumano colaboradorHumano) {
    return suscripcions.stream().anyMatch(suscripcion -> suscripcion.getColaboradorHumano().equals(colaboradorHumano));
  }

  public Suscripcion obtenerSuscripcion(ColaboradorHumano colaboradorHumano, TipoSuscripcion tipoSuscripcion) {
    return suscripcions.stream().filter(suscripcion ->
        suscripcion.getColaboradorHumano().equals(colaboradorHumano) && suscripcion.getTipoSuscripcion() == tipoSuscripcion)
        .findFirst().orElse(null);
  }

  public String getFechaFuncionamientoFormateada() {
    if (fechaFuncionamiento == null) {
      return "Fecha no disponible";
    }
    return fechaFuncionamiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }


}
