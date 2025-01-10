package dominio.notificador.mediosDeContacto;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.notificador.Notificacion;
import dominio.notificador.TipoNotificacion;
import dominio.notificador.mailSender.AdapterJakartaMail;
import dominio.notificador.mailSender.MailSender;
import dominio.repositorios.RepositorioNotificaciones;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@DiscriminatorValue("MAIL")
@Entity
public class ContactoMail extends MedioContacto{

  @Transient
  private MailSender mailSender;

  public ContactoMail(){
    this.mailSender = new MailSender(new AdapterJakartaMail());
  }

  public void notificar(Notificacion notificacion, ColaboradorHumano colaboradorHumano){
    notificacion.setTipoNotificacion(TipoNotificacion.MAIL);
    colaboradorHumano.addNotificacion(notificacion);
    //mailSender.notificar(contenido, notificacion.getMensaje(), notificacion.getAsunto());
    RepositorioNotificaciones.getInstance().persistir(notificacion);
  }
}
