package dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras;

import dominio.entidades.heladera.Heladera;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CriterioVacias implements CriterioFiltroHeladeras {

  public List<Heladera> aplicarFiltro(List<Heladera> heladerasCercanas, Heladera heladera) {
    return heladerasCercanas.stream()
        .sorted(Comparator.comparingInt(Heladera::capacidadRestante).reversed())
        .collect(Collectors.toList());
  }
}
