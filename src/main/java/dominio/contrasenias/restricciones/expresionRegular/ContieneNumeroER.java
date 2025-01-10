package dominio.contrasenias.restricciones.expresionRegular;

public class ContieneNumeroER extends ExpresionRegular{

  private static final String expresionRegular = ".*\\d.*";

  @Override
  protected String obtenerExpresionRegular() {
    return expresionRegular;
  }

  @Override
  public String getMensajeError() {
    return super.getMensajeError() + " No contiene numero.";
  }
}
