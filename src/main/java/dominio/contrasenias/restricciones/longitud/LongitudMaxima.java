package dominio.contrasenias.restricciones.longitud;

import dominio.contrasenias.restricciones.Restriccion;
import dominio.entidades.persona.autentificacion.Usuario;

public class LongitudMaxima implements Restriccion {
  private static final Integer tamanioMaximo = 64;

  @Override
  public boolean esValidaContrasenia(Usuario usuario) {
    return usuario.getPassword().length() <= tamanioMaximo;
  }

  @Override
  public String getMensajeError() {
    return "La contraseña debe tener como maximo " + tamanioMaximo + " caracteres.";
  }
}
