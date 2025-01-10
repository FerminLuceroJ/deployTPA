package dominio.notificador.mediosDeContacto;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.notificador.Notificacion;
import dominio.notificador.TipoNotificacion;
import dominio.notificador.messageSender.InstantMessage.InstantMessageApp;
import dominio.notificador.messageSender.WhatsAppSender;
import dominio.repositorios.RepositorioNotificaciones;
import lombok.Getter;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@DiscriminatorValue("WPP")
@Entity
public class ContactoWPP extends MedioContacto{

  @Transient
  private WhatsAppSender whatsAppSender;

  public ContactoWPP(){
    this.whatsAppSender = new WhatsAppSender();
  }

  public void notificar(Notificacion notificacion, ColaboradorHumano colaboradorHumano){
    notificacion.setTipoNotificacion(TipoNotificacion.WPP);
    colaboradorHumano.addNotificacion(notificacion);
    whatsAppSender.notificar(InstantMessageApp.WHATSAPP,contenido, notificacion.getMensaje());
    RepositorioNotificaciones.getInstance().persistir(notificacion);
  }
}
