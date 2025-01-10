package dominio.contrasenias.restricciones;

import dominio.lectorArchivos.LectorArchivos;
import dominio.entidades.persona.autentificacion.Usuario;
import java.util.List;

public class ListaDeContraseniasDebiles implements Restriccion {
  private static final String nombreDeArchivo = "archivos/ListaPeoresContrasenias.txt";
  private final List<String> peoresContrasenias;

  @Override
  public boolean esValidaContrasenia(Usuario usuario) {
    return !this.peoresContrasenias.contains(usuario.getPassword());
  }

  @Override
  public String getMensajeError() {
    return "La contraseña se encuentra en la lista de las 'Diez mil peores contraseñas'.";
  }

  public ListaDeContraseniasDebiles() {
    LectorArchivos<String> lector = new LectorArchivos<>();
    this.peoresContrasenias = lector.leerDesdeArchivo(nombreDeArchivo, String::trim);
  }
}