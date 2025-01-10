package dominio.contrasenias.restricciones.longitud;

import dominio.contrasenias.restricciones.Restriccion;
import dominio.entidades.persona.autentificacion.Usuario;

public class LongitudMinima implements Restriccion {
  private static final Integer tamanioMinimo = 8;

  @Override
  public boolean esValidaContrasenia(Usuario usuario) {
    return usuario.getPassword().length() >= tamanioMinimo;
  }

  @Override
  public String getMensajeError() {
    return "La contraseña debe tener al menos " + tamanioMinimo + " caracteres.";
  }
}

