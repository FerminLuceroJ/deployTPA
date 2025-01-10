package controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginacionController {

  public static class Pagina {
    private int numeroPagina;
    private boolean esActual;

    public Pagina(int numeroPagina, boolean esActual) {
      this.numeroPagina = numeroPagina;
      this.esActual = esActual;
    }

    public int getNumeroPagina() {
      return numeroPagina;
    }

    public boolean isEsActual() {
      return esActual;
    }
  }

  public static class Paginacion<T> {
    private List<Pagina> paginas;
    private List<T> elementosPagina;
    private int totalPaginas;
    private int paginaActual;
    private boolean hasItems;

    public Paginacion(List<Pagina> paginas, List<T> elementosPagina, int totalPaginas, int paginaActual, boolean hasItems) {
      this.paginas = paginas;
      this.elementosPagina = elementosPagina;
      this.totalPaginas = totalPaginas;
      this.paginaActual = paginaActual;
      this.hasItems = hasItems;
    }

    public List<Pagina> getPaginas() {
      return paginas;
    }

    public List<T> getElementosPagina() {
      return elementosPagina;
    }

    public int getTotalPaginas() {
      return totalPaginas;
    }

    public int getPaginaActual() {
      return paginaActual;
    }

    public boolean isHasItems() {
      return hasItems;
    }
  }

  public static <T> Paginacion<T> calcularPaginacion(List<T> elementos, int elementosPorPagina, int paginaActual) {
    int totalElementos = elementos.size();
    int totalPaginas = (int) Math.ceil((double) totalElementos / elementosPorPagina);
    boolean hasItems = totalElementos > 0;

    if (paginaActual < 1) paginaActual = 1;
    if (paginaActual > totalPaginas) paginaActual = totalPaginas;

    int inicio = (paginaActual - 1) * elementosPorPagina;
    int fin = Math.min(inicio + elementosPorPagina, totalElementos);
    List<T> elementosPagina = elementos.subList(inicio, fin);

    int finalPaginaActual = paginaActual;
    List<Pagina> paginas = IntStream.rangeClosed(1, totalPaginas)
        .mapToObj(pagina -> new Pagina(pagina, pagina == finalPaginaActual))
        .collect(Collectors.toList());

    return new Paginacion<>(paginas, elementosPagina, totalPaginas, paginaActual, hasItems);
  }
}