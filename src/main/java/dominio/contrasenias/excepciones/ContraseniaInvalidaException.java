package dominio.contrasenias.excepciones;

public class ContraseniaInvalidaException extends RuntimeException {

  public ContraseniaInvalidaException(String message) {
    super(message);
  }
}
