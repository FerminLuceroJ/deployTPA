package dominio.notificador.mediosDeContacto;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.notificador.Notificacion;
import dominio.notificador.TipoNotificacion;
import dominio.notificador.messageSender.InstantMessage.InstantMessageApp;
import dominio.notificador.messageSender.TelegramSender;
import dominio.repositorios.RepositorioNotificaciones;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@DiscriminatorValue("TELEGRAM")
@Entity
public class ContactoTelegram extends MedioContacto{

  @Transient
  private TelegramSender telegramSender;

  public ContactoTelegram() {
    this.telegramSender = new TelegramSender();
  }

  public void notificar(Notificacion notificacion, ColaboradorHumano colaboradorHumano) {
    notificacion.setTipoNotificacion(TipoNotificacion.TELEGRAM);
    colaboradorHumano.addNotificacion(notificacion);
    telegramSender.notificar(InstantMessageApp.TELEGRAM, contenido, notificacion.getMensaje());
    RepositorioNotificaciones.getInstance().persistir(notificacion);
  }
}
