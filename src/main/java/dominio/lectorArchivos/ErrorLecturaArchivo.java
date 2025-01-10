package dominio.lectorArchivos;

public class ErrorLecturaArchivo extends RuntimeException {
  public ErrorLecturaArchivo(String message) {
    super(message);
  }
}