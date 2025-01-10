package dominio.reportes;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.persona.colaborador.Colaborador;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioColaboradores;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorReportes {

  public List<Reporte> generarReporte() {

    ArrayList<Reporte> reportes;

    reportes = RepositorioColaboradores.getInstance().buscar().stream()
        .map(colaborador -> {
          double puntaje = calcularTotalPuntaje(colaborador);
          return new Reporte(colaborador, LocalDate.now(), puntaje);
        })
        .collect(Collectors.toCollection(ArrayList::new));

    return reportes;
  }

  public Double calcularTotalPuntaje(Colaborador colaborador) {

    double puntaje = 0.00;

    List<Colaboracion> colaboraciones = RepositorioColaboraciones.getInstance().buscarPorIdColaborador(colaborador.getId());

    puntaje = colaboraciones.stream().map(Colaboracion::calcularPuntaje).reduce(Double::sum).get();

    /*for (Colaboracion colaboracion : RepositorioColaboraciones.getInstance().buscarPorIdColaborador(colaborador.getId())) {
      puntaje += colaboracion.calcularPuntaje();
    }*/

    return puntaje;
  }

}
