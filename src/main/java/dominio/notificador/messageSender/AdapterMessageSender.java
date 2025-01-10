package dominio.notificador.messageSender;

import dominio.notificador.messageSender.InstantMessage.InstantMessageApp;
import dominio.notificador.messageSender.InstantMessage.InstantMessageSender;
import dominio.notificador.messageSender.InstantMessage.InvalidTelephoneNumberException;

public class AdapterMessageSender implements MensajeSender{

  private InstantMessageSender instantMessageSender;

  public AdapterMessageSender(InstantMessageSender instantMessageSender) {
    this.instantMessageSender = instantMessageSender;
  }

  @Override
  public void notificar(InstantMessageApp instantMessageApp, String telefono, String mensaje) throws InvalidTelephoneNumberException {
    try {
      instantMessageSender.sendMessage(instantMessageApp, telefono, mensaje);
    } catch (Exception e) {
      throw new InvalidTelephoneNumberException("Hubo un error en el envío del mensaje: " + e.getMessage());
    }
  }

}

