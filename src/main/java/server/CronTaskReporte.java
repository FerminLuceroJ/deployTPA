package server;

import dominio.reportes.GeneradorReportes;
import dominio.reportes.ReportTask;
import java.util.Timer;
import java.util.TimerTask;

public class CronTaskReporte {


  public void run() {
    Timer timer = new Timer();
    TimerTask reportTask = new ReportTask(new GeneradorReportes());

    // Esto despues lo tengo que modificar para setear la frecuencia del reporte (milisegundos).
    long delay = 0;
    long period = 2 * 60 * 60 * 1000;

    timer.schedule(reportTask, delay, period);
  }
}
