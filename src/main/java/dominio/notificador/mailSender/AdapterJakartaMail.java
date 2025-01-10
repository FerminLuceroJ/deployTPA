package dominio.notificador.mailSender;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.security.InvalidParameterException;
import java.util.Properties;

public class AdapterJakartaMail implements AdapterMailSender {

    private Properties propiedades;
    private Session sesion;

    public AdapterJakartaMail() {
      String username = "2b89dbe85562e9";
      String password = "18687f366d0d06";

      propiedades = new Properties();
      this.cargarConfig();
      this.sesion = Session.getInstance(propiedades,  new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);}});
    }

    public void cargarConfig() {
      // Seteo propiedades del config
      this.propiedades.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
      this.propiedades.put("mail.smtp.port", "2525");
      this.propiedades.put("mail.smtp.starttls.enable", "true");
      this.propiedades.put("mail.smtp.user", "2b89dbe85562e9");
      this.propiedades.put("mail.smtp.password", "18687f366d0d06");
      this.propiedades.put("mail.smtp.auth", "true");



      // Verifico que se cargo algo
     // this.verificarConfig();
    }

    private void verificarConfig() throws InvalidParameterException {
      String[] keys = {"mail.smtp.auth",
                       "mail.smtp.starttls.enable",
                       "mail.smtp.host",
                       "mail.smtp.port",
                       "mail.smtp.ssl.trust"};
                       //"mail.smtp.socketFactory.class"};

      for (String key : keys) {
        if (this.propiedades.containsKey(key)) {
          throw new InvalidParameterException("No existe esa clave " + key);
        }
      }
    }

    @Override
    public void notificar(String correo, String mensaje, String asunto) {

      Message message = new MimeMessage(sesion);
    try {
       message.setFrom(new InternetAddress("api"));
       message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
       message.setSubject(asunto);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
       mimeBodyPart.setContent(mensaje, "text/html; charset=utf-8");

       Multipart multipart = new MimeMultipart();
       multipart.addBodyPart(mimeBodyPart);

       message.setContent(multipart);

       Transport.send(message);
       } catch (MessagingException e) {
          throw new RuntimeException("Algo salio mal: " + e.getMessage());
         }
    }
}

