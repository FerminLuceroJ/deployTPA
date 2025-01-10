package dominio.entidades.heladera.aperturas;

public interface ControladorAccesoExterno {
  void notificarTarjetasColaboradorHabilitada(String numerosDeTarjeta);

  void quitarPermisoTarjeta(String numerosDeTarjeta);

  void abrirHeladera(String numeroTarjeta); //Discutir si este va o no

  /**   ALTERNATIVAS PROPUESTAS:
   *
   *  Alt1:
   *
   *  metodo que ejecuta determinadas acciones de la heladera (seteables) cuando una tarjeta ingresa
   *
   *  void onIngresoDeTarjeta(Action action) ...
   *                                                    // Hay inversion de control //
   *  luego el action tendria el metodo
   *
   *  void executeForTarjetaIngresada(String numeroTarjeta) ...
   *
   *  Alt2:
   *
   *  metodo que llama a la heladera para abrirse, validando internamente que la tarjeta tiene acceso
   *
   *  void abrirHeladera(String numedroTarjeta)
   *
   *  Alt3:
   *
   *  delegar logica de permisos heladera a la heldera.
   *  controladorDeAcceso solo se encarga de:
   *
   *  void notificarTarjetaPermitida(String numeroTarjeta)
   *
   *  void quitarPermisoTarjeta(String numeroTarjeta)
   *
   *  void abrirHeladera(String numeroTarjeta)
   *
   *  heladera tiene lista con tarjetas permitidas y valida cuando se quiere abrir
   *
   *
   *
   */
}
