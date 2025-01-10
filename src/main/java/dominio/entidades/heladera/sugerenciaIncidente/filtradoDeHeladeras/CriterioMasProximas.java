package dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras;

import dominio.entidades.heladera.Heladera;
import dominio.localizacion.Localizacion;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CriterioMasProximas implements CriterioFiltroHeladeras {

  public List<Heladera> aplicarFiltro(List<Heladera> heladerasCercanas,Heladera heladera) {
    Localizacion localizacionPrincipal = heladera.getLocalizacion();
    return heladerasCercanas.stream()
        .sorted(Comparator.comparingDouble(h -> localizacionPrincipal.distanciaEntrePuntos(h.getLocalizacion())))
        .collect(Collectors.toList());
  }
}
