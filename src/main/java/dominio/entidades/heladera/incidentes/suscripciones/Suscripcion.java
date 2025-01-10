package dominio.entidades.heladera.incidentes.suscripciones;

import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras.GeneradorSugerenciasIncidente;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.repositorios.RepositorioSugerencias;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import dominio.notificador.Notificacion;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Suscripcion {

  @Id @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private TipoSuscripcion tipoSuscripcion;
  @Column
  private int cantidadViandasNotificacion;
  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private ColaboradorHumano colaboradorHumano;
  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id") // Relación con la heladera
  private Heladera heladera; // La suscripción conoce a la heladera

  public Suscripcion() {}

  public Suscripcion(TipoSuscripcion tipoSuscripcion, int cantidadViandasNotificacion, ColaboradorHumano colaboradorHumano){
    this.tipoSuscripcion = tipoSuscripcion;
    this.cantidadViandasNotificacion = cantidadViandasNotificacion;
    this.colaboradorHumano = colaboradorHumano;
  }

  public void notificar(Heladera heladera) {
    if(tipoSuscripcion == TipoSuscripcion.CANTIDAD_DE_VIANDAS && cantidadViandasNotificacion <= heladera.cantidadDeViandas()) {
      String asunto = "Viandas en heladera nro: ".concat(heladera.getSerialNumber());
      String mensaje = "Cantidad de viandas: ".concat(String.valueOf(heladera.cantidadDeViandas()));
      Notificacion notificacion = new Notificacion(colaboradorHumano, mensaje, asunto, null);
      colaboradorHumano.getMedioDeContactoFavorito().notificar(notificacion, colaboradorHumano);

    } else if(tipoSuscripcion == TipoSuscripcion.REPORTE_INCIDENTE) {

      String asunto = "Incidente en ".concat(heladera.getNombreSignificativo());
      SugerenciaMoverViandas sugerencia = GeneradorSugerenciasIncidente.getInstancia().generarSugerenciaMoverViandas(heladera);
      RepositorioSugerencias.getInstance().persistir(sugerencia);
      Notificacion notificacion = new Notificacion(colaboradorHumano, "Ocurrio un incidente en una heladera que es de tu interes", asunto, sugerencia);
      colaboradorHumano.getMedioDeContactoFavorito().notificar(notificacion, colaboradorHumano);
    }
  }

  public boolean esCantidadViandas() {
    return (tipoSuscripcion == TipoSuscripcion.CANTIDAD_DE_VIANDAS);
  }

}
