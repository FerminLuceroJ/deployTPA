package dominio.notificador.mailSender;

public class MailSender {

  private final AdapterMailSender adapterMailSender;

  public MailSender(AdapterMailSender adapter) {
    this.adapterMailSender = adapter;
  }

  public void notificar(String correoDestino, String mensaje, String asunto) {
    this.adapterMailSender.notificar(correoDestino, mensaje, asunto);
  }
}
