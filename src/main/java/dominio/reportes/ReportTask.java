package dominio.reportes;

import dominio.repositorios.RepositorioReportes;
import java.util.List;
import java.util.TimerTask;

public class ReportTask extends TimerTask {

  private final GeneradorReportes generadorReportes;

  public ReportTask(GeneradorReportes generadorReportes) {
    this.generadorReportes = generadorReportes;
  }

  @Override
  public void run() {
    List<Reporte> reportes = generadorReportes.generarReporte();

    for (Reporte reporte : reportes) {
      RepositorioReportes.getInstance().persistir(reporte);
    }
   // System.out.println("Se guardaron " + reportes.size() + " reportes");
  }

}
