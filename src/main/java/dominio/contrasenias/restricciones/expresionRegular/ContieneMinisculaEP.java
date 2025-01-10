package dominio.contrasenias.restricciones.expresionRegular;

public class ContieneMinisculaEP extends ExpresionRegular {

  private static final String expresionRegular = ".*[a-z].*";

  @Override
  protected String obtenerExpresionRegular() {
    return expresionRegular;
  }

  @Override
  public String getMensajeError() {
    return super.getMensajeError() + " No contiene minuscula.";
  }
}
