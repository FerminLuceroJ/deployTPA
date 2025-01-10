package dominio.entidades.heladera.sugerenciaIncidente.filtradoDeHeladeras;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import dominio.localizacion.Localizacion;
import dominio.repositorios.RepositorioNotificaciones;
import lombok.Setter;
import dominio.repositorios.RepositorioHeladeras;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorSugerenciasIncidente {

  private static GeneradorSugerenciasIncidente instancia;
  @Setter
  private CriterioFiltroHeladeras criterioFiltroHeladeras = new CriterioAlAzar();


  private GeneradorSugerenciasIncidente() {
  }

  public static GeneradorSugerenciasIncidente getInstancia() {
    if (instancia == null) {
      instancia = new GeneradorSugerenciasIncidente();
    }
    return instancia;
  }

  public List<Heladera> generarHeladerasParaTransferir(Heladera heladera){

    List<Heladera> todasLasHeladeras = RepositorioHeladeras.getInstance().buscar();

    List<Heladera> heladerasFiltradas = new ArrayList<>(todasLasHeladeras);
    heladerasFiltradas.remove(heladera);

    List<Heladera> heladerasCercanas = this.getHeladerasCercanas(heladerasFiltradas,heladera);

    List<Heladera> heladerasOrdenadas = this.getHeladerasOrdenadas(heladerasCercanas,heladera);

    return this.getHeladerasCapacidadMayor(heladerasOrdenadas,heladera);
  }

  public List<Heladera> getHeladerasCercanas(List<Heladera> heladerasFiltradas,Heladera heladera){
    Localizacion localizacionPrincipal = heladera.getLocalizacion();

    return  heladerasFiltradas.stream()
        .filter(h -> localizacionPrincipal.distanciaEntrePuntos(h.getLocalizacion()) < 10)
        .collect(Collectors.toList());
  }

  public List<Heladera> getHeladerasCapacidadMayor(List<Heladera> heladerasOrdenadas, Heladera heladera){
    int cantidadDeViandas = heladera.cantidadDeViandas();
    int capacidadTotal = 0;
    List<Heladera> heladerasSeleccionadas = new ArrayList<>();
    for (Heladera h : heladerasOrdenadas) {
      if (capacidadTotal >= cantidadDeViandas) {
        break;
      }
      heladerasSeleccionadas.add(h);
      capacidadTotal += h.capacidadRestante();
    }

    return heladerasSeleccionadas;
  }

  public List<Heladera> getHeladerasOrdenadas(List<Heladera> heladerasCercanas,Heladera heladera) {
    return criterioFiltroHeladeras.aplicarFiltro(heladerasCercanas, heladera);
  }
  
  public SugerenciaMoverViandas generarSugerenciaMoverViandas(Heladera heladeraDaniada) {
    return new SugerenciaMoverViandas(heladeraDaniada, this.generarHeladerasParaTransferir(heladeraDaniada));
  }
}
