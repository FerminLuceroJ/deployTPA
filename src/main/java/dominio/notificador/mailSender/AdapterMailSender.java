package dominio.notificador.mailSender;

public interface AdapterMailSender {

  void notificar(String correo, String mensaje, String asunto);
}
