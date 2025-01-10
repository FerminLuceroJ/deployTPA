/*package entidades;

import dominio.notificador.Notificacion;
import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.ContactoTelegram;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.repositorios.RepositorioNotificaciones;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class NotificadorTest {

  private ContactoMail contactoMail;
  private ContactoWPP contactoWPP;
  private ContactoTelegram contactoTelegram;
  private ColaboradorHumano colaboradorNotificado;


  public void agregarMedioDeContacto(ColaboradorHumano colaborador, MedioContacto mediosContacto) {

    colaborador.getMediosDeContacto().add(mediosContacto);
  }

  @BeforeEach
  void setUp() {

    contactoMail = new ContactoMail();
    contactoWPP = new ContactoWPP();
    contactoTelegram = new ContactoTelegram();

    colaboradorNotificado = new ColaboradorHumano(
                                        "Corrientes 555",
                                        "12345678",
                                        "DNI",
                                        "Juan Perez",
                                        null,
                                        new ArrayList<MedioContacto>(),
                                        new ContactoMail()
        );
  }

  @Test
  void seEnviaNotificacionWhatsAppCorrectamente() {

    Notificacion notificacion = new Notificacion(colaboradorNotificado, "Esto es un mensaje por WhatsApp", null);
    contactoWPP.notificar(notificacion, notificacion.getColaborador());

    Assertions.assertEquals(RepositorioNotificaciones.getInstance().buscar().get(0).getMensaje(), "Esto es un mensaje por WhatsApp");

  }

  @Test
  void seEnviaNotificacionTelegramCorrectamente() {

    //doNothing().when(telegramSender).notificar(eq(InstantMessageApp.TELEGRAM), anyString(), anyString());
    MedioContacto medioTelegram = new ContactoTelegram();

    this.agregarMedioDeContacto(colaboradorNotificado, medioTelegram);
    Notificacion notificacion = new Notificacion(colaboradorNotificado, "Esto es un mensaje por Telegram", null);

    contactoTelegram.notificar(notificacion, notificacion.getColaborador());

    Assertions.assertEquals(RepositorioNotificaciones.getInstance().buscar().get(0).getMensaje(),"Esto es un mensaje por Telegram");
  }

  @Test
  void seEnviaNotificacionMailCorrectamente() {

    Notificacion notificacion = new Notificacion(colaboradorNotificado, "Esto es un mensaje por Mail", "sarasa");

    contactoMail.notificar(notificacion, notificacion.getColaborador());

    Assertions.assertEquals(RepositorioNotificaciones.getInstance().buscar().size(), 1);
    Assertions.assertEquals(RepositorioNotificaciones.getInstance().buscar().get(0).getAsunto(), "sarasa");
  }

}
*/