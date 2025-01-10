package dominio.contrasenias.restricciones.expresionRegular;

public class ContieneMayusculaER extends ExpresionRegular {

  private static final String expresionRegular = ".*[A-Z].*";

  @Override
  protected String obtenerExpresionRegular() {
    return expresionRegular;
  }

  @Override
  public String getMensajeError() {
    return super.getMensajeError() + "No contiene mayuscula.";
  }
}
