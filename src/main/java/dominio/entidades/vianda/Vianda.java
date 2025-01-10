package dominio.entidades.vianda;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

@Getter @Setter
@Entity
public class Vianda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "fechaCaducidad")
  private LocalDate fechaCaducidad;
  @Column(name = "fechaDonacion")
  private LocalDate fechaDonacion;
  @Column(name = "calorias")
  private Integer calorias;
  @Column(name = "peso")
  private Float peso;
  @Enumerated(EnumType.STRING)
  private Estado estado;
  @Enumerated(EnumType.STRING)
  private TipoComida tipoComida;
  @ManyToOne
  @JoinColumn(name = "id_heladera_ubicada", referencedColumnName = "id")
  private Heladera heladeraUbicada;
  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private Colaborador colaborador;

  public Vianda(
      LocalDate fechaCaducidad,
      LocalDate fechaDonacion,
      Heladera heladeraUbicada,
      Integer calorias,
      Float peso,
      Estado estado,
      TipoComida tipoComida,
      Colaborador colaborador
  ) {
    if (fechaCaducidad == null) {
      throw new RuntimeException("Se debe ingresar la fecha de caducidad");
    }

    if (fechaDonacion == null) {
      throw new RuntimeException("Se debe ingresar la fecha de donacion");
    }

    if (heladeraUbicada == null) {
      throw new RuntimeException("Se debe ingresar la heladera ubicada");
    }

    if (estado == null) {
      throw new RuntimeException("Se debe ingresar el estado");
    }

    if (tipoComida == null) {
      throw new RuntimeException("Se debe ingresar la comida");
    }

    if (colaborador == null) {
      throw new RuntimeException("Se debe ingresar el colaborador");
    }

    this.fechaCaducidad = fechaCaducidad;
    this.fechaDonacion = fechaDonacion;
    this.heladeraUbicada = heladeraUbicada;
    this.calorias = calorias;
    this.peso = peso;
    this.estado = estado;
    this.tipoComida = tipoComida;
    this.colaborador = colaborador;
  }

  public Vianda() {
  }

  public Boolean estaFresca() {
    return !fechaCaducidad.isBefore(LocalDate.now());
  }

  public boolean fueEntregada() {
    return estado == Estado.ENTREGADA;
  }

}
