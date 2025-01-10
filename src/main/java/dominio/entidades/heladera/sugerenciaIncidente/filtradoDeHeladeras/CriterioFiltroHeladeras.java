package dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras;

import dominio.entidades.heladera.Heladera;
import java.util.List;

public interface CriterioFiltroHeladeras {
  List<Heladera> aplicarFiltro(List<Heladera> heladerasCercanas, Heladera heladeraPrincipal);
}
