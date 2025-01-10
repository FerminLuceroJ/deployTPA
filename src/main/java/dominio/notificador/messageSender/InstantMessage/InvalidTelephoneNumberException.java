package dominio.notificador.messageSender.InstantMessage;

public class InvalidTelephoneNumberException extends Exception {
  public InvalidTelephoneNumberException(String message) {
    super(message);
  }
}