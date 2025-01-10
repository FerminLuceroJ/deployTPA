package dominio.entidades.heladera.sensorCantidadViandas;

public class ListenerCantidadViandas {
  public ListenerCantidadViandas() {}

  /*public void notificarLimiteCantidadViandas(int newQuantity) {TODO
    List<SuscripcionViandas> suscripcionesAViandas = RepositorioSuscripciones.getInstancia().getSuscripcionesViandas();
    List<SuscripcionViandas> suscriptosAHeladera = suscripcionesHeladera.stream().filter(suscripto -> suscripto.getHeladera().equals(heladera)).toList();
    suscriptosLimiteViand.forEach(suscripcion -> this.validarNotificacion(suscripcion, newQuantity));
  }

  public void validarNotificacion(SuscripcionViandas suscripcionVianda, int newQuantity) {
    if(suscripcion.getCantidadViandasANotificar() <= newQuantity)
      suscripcion.notificarColaborador();
  }*/
}
