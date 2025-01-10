package dominio.notificador.messageSender;

import dominio.notificador.messageSender.InstantMessage.InstantMessageApp;
import dominio.notificador.messageSender.InstantMessage.InvalidTelephoneNumberException;

public interface MensajeSender {

  void notificar(InstantMessageApp instantMessageApp, String telefono, String mensaje) throws InvalidTelephoneNumberException;
}
