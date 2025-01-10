package dominio.lectorArchivos.lecturaCSV;

import dominio.lectorArchivos.LectorArchivos;
import java.util.HashMap;
import java.util.Map;

public class CargaCoeficientes {
  private Map<String, Double> coeficientes = new HashMap<>();
  private LectorArchivos<Map.Entry<String, Double>> lectorArchivos;

  public CargaCoeficientes() {
    this.lectorArchivos = new LectorArchivos<>();
  }

  public void cargarCoeficientes(String filePath) {
    lectorArchivos.leerDesdeArchivo(filePath, this::procesarLinea).forEach(entry -> coeficientes.put(entry.getKey(), entry.getValue()));
  }

  private Map.Entry<String, Double> procesarLinea(String linea) {
    String[] parts = linea.split(",");
    if (parts.length == 2) {
      return new HashMap.SimpleEntry<>(parts[0], Double.parseDouble(parts[1]));
    }
    return null;
  }

  public double getCoeficiente(String operation) {
    return coeficientes.getOrDefault(operation, 0.0);
  }
}
