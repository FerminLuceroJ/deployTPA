package dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras;

import dominio.entidades.heladera.Heladera;

import java.util.Collections;
import java.util.List;

public class CriterioAlAzar implements CriterioFiltroHeladeras {

  public List<Heladera> aplicarFiltro(List<Heladera> heladerasCercanas, Heladera heladera) {
    Collections.shuffle(heladerasCercanas);
    return heladerasCercanas;
  }
}
