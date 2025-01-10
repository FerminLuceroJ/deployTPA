package dominio.entidades.heladera.incidentes.suscripciones;

import dominio.entidades.heladera.Heladera;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum TipoSuscripcion {
  CANTIDAD_DE_VIANDAS,
  REPORTE_INCIDENTE;

  public static List<Map<String, String>> toArray() {
    return Arrays.stream(TipoSuscripcion.values())
        .map(tipo -> Map.of(
            "nombre", formatNombre(tipo.name()),
            "valor", tipo.name()
        ))
        .collect(Collectors.toList());
  }

  // Método para dar formato al nombre
  private static String formatNombre(String name) {
    return name.replace("_", " ").toLowerCase()
        .replaceFirst(Character.toString(name.charAt(0)).toLowerCase(),
            Character.toString(name.charAt(0)).toUpperCase());
  }

  public static void main(String[] args) {
    List<Map<String, String>> arrayDeObjetos = TipoSuscripcion.toArray();
    System.out.println(arrayDeObjetos); // Imprime el array de objetos
  }
}
