package dominio.entidades.tarjeta.solicitudes;

import dominio.entidades.tarjeta.TarjetaColaborador;

public interface Solicitud {
  void aceptarSolicitud(TarjetaColaborador tarjetaColaborador);
}
