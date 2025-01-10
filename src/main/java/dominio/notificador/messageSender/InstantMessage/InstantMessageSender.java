package dominio.notificador.messageSender.InstantMessage;

public interface InstantMessageSender {

  void sendMessage(
      InstantMessageApp provider,
      String telephone,
      String message
  ) throws InvalidTelephoneNumberException;
}
