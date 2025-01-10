package dominio.entidades.heladera.incidentes;

import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.notificador.Notificacion;
import java.util.List;
import lombok.Getter;

@Getter
public class NotificadorSuscriptos {

  public static NotificadorSuscriptos instancia;

  private NotificadorSuscriptos() {
  }

  public static NotificadorSuscriptos getInstancia() {
    if (instancia == null) {
      instancia = new NotificadorSuscriptos();
    }
    return instancia;
  }

}
