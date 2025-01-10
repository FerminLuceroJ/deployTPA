package dominio.contrasenias.restricciones.expresionRegular;

import dominio.contrasenias.restricciones.Restriccion;
import dominio.entidades.persona.autentificacion.Usuario;

public abstract class ExpresionRegular implements Restriccion {

  @Override
  public boolean esValidaContrasenia(Usuario usuario) {
    return usuario.getPassword().matches(obtenerExpresionRegular());
  }

  @Override
  public String getMensajeError() {
    return "La contraseña debe cumplir la expresión regular: " + obtenerExpresionRegular() + " .";
  }

  protected abstract String obtenerExpresionRegular();

}

