package dominio.entidades.heladera.aperturas;

import dominio.entidades.tarjeta.TarjetaColaborador;
import lombok.Getter;

@Getter
public class AdapterControladorAcceso {
  private final ControladorAccesoExterno controladorAccesoExterno;

  public AdapterControladorAcceso(ControladorAccesoExterno controladorAcceso) {
    this.controladorAccesoExterno = controladorAcceso;
  }

  public void notificarTarjetasColaboradorHabilitada(TarjetaColaborador tarjetaColaborador){
    controladorAccesoExterno.notificarTarjetasColaboradorHabilitada(tarjetaColaborador.getCodigoAlfaNumerico());
  }

  public void quitarPermisoTarjeta(TarjetaColaborador tarjetaColaborador) {
    controladorAccesoExterno.quitarPermisoTarjeta(tarjetaColaborador.getCodigoAlfaNumerico());
  }

  public void abrirHeladera(TarjetaColaborador tarjetaColaborador) {
    controladorAccesoExterno.abrirHeladera(tarjetaColaborador.getCodigoAlfaNumerico());
  }
}

