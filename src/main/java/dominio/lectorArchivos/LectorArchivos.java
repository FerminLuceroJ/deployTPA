package dominio.lectorArchivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LectorArchivos<T> {

  public List<T> leerDesdeArchivo(String rutaArchivo, Function<String, T> procesarLinea) {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaArchivo)) {
      if (inputStream == null) {
        throw new ErrorLecturaArchivo("El archivo no fue encontrado: " + rutaArchivo);
      }
      return leerDesdeStream(inputStream, procesarLinea);
    } catch (IOException e) {
      throw new ErrorLecturaArchivo("Error leyendo el archivo: " + e.getMessage());
    }
  }

  public List<T> leerDesdeStream(InputStream entrada, Function<String, T> procesarLinea) {
    List<T> resultados = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(entrada))) {
      String linea;
      while ((linea = reader.readLine()) != null) {
        T item = procesarLinea.apply(linea);
        if (item != null) {
          resultados.add(item);
        }
      }
    } catch (IOException e) {
      throw new ErrorLecturaArchivo("Error leyendo el archivo: " + e.getMessage());
    }

    return resultados;
  }


}
