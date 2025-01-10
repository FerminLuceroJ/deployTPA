package dominio.contrasenias.restricciones;

import dominio.entidades.persona.autentificacion.Usuario;

public interface Restriccion {

  public boolean esValidaContrasenia(Usuario usuario);
  public String getMensajeError();


}
