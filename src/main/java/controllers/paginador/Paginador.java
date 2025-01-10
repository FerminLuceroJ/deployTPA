package controllers.paginador;

import java.util.List;
import java.util.Map;

public class Paginador {
  private static Paginador instance;

  private Paginador() {}

  public static Paginador getInstance() {
    if (instance == null) {
      instance = new Paginador();
    }
    return instance;
  }

  public <T> Map<String, Object> paginar(List<T> items, int paginaActual, int itemsPorPagina) {
    int totalItems = items.size();
    boolean hasItems = totalItems > 0;
    int paginasTotales = (int) Math.ceil((double) totalItems / itemsPorPagina);
    int inicio = Math.max((paginaActual - 1) * itemsPorPagina, 0);
    int fin = Math.min(inicio + itemsPorPagina, totalItems);
    List<T> itemsPagina = items.subList(inicio, fin);
    return Map.of(
        "items", itemsPagina,
        "paginaActual", paginaActual,
        "totalPaginas", paginasTotales,
        "hasItems", hasItems
    );
  }
}