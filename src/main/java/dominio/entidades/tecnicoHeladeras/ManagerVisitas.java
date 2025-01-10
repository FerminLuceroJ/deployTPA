package dominio.entidades.tecnicoHeladeras;

import dominio.entidades.heladera.Heladera;
import java.util.List;
import dominio.repositorios.RepositorioTecnicosHeladeras;

public class ManagerVisitas {
  private static ManagerVisitas instancia;
  private RepositorioTecnicosHeladeras repositorioTecnicosHeladeras;
  private ManagerVisitas(){}

  public static ManagerVisitas getInstancia(){
    if(instancia == null) {
      instancia = new ManagerVisitas();
    }
    return instancia;
  }

  public void notificarTecnico(Heladera heladera) {
    TecnicoHeladera tecnicoCercano = this.obtenerTecnicoMasCercanoA(heladera);
    tecnicoCercano.agregarSolicitudVisita(new SolicitudVisita(heladera, tecnicoCercano));
  }

  public TecnicoHeladera obtenerTecnicoMasCercanoA(Heladera heladera) {
    TecnicoHeladera tecnicoMasCercano = null;
    double distanciaInicial = Double.MAX_VALUE;

    List<TecnicoHeladera> tecnicosHeladeras = RepositorioTecnicosHeladeras.getInstance().buscar();
/** Cambiar por algo mas declarativo a nivel listas
    Usar repo para obtener directo al tecnico mas cercano
    Hacer que la query la haga directamente el repo por cercania a heladera
    Cuando usemos el repo para eso ya deja de tener sentido ESTA ENTIDAD
 */
    for(TecnicoHeladera tecnico : tecnicosHeladeras) {
      if(heladera.getLocalizacion().distanciaEntrePuntos(tecnico.getLocalizacion()) < distanciaInicial) {
        tecnicoMasCercano = tecnico;
        distanciaInicial = heladera.getLocalizacion().distanciaEntrePuntos(tecnico.getLocalizacion());
      }
    }
    return tecnicoMasCercano;
  }
}
