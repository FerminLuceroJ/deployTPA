package dominio.lectorArchivos.lecturaCSV;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.lectorArchivos.LectorArchivos;
import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioColaboraciones;
import dominio.repositorios.RepositorioColaboradores;
import dominio.repositorios.RepositorioMediosDeContacto;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CargaColaboradoresCSV {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private final LectorArchivos<ColaboradorHumano> lectorArchivo;

  public CargaColaboradoresCSV() {
    this.lectorArchivo = new LectorArchivos<>();
  }

  public void cargarColaboradorCSVArchivo(String rutaArchivo) {
    lectorArchivo.leerDesdeArchivo(rutaArchivo, this::procesarLinea);
  }

  public void cargarColaboradorCSVWeb(InputStream entrada) {
    lectorArchivo.leerDesdeStream(entrada, this::procesarLinea);
  }

  private ColaboradorHumano procesarLinea(String linea) {
    String[] datos = linea.split(",");
    if (datos.length == 8) {
      String tipoDoc = datos[0];
      String documento = datos[1];
      String nombre = datos[2];
      String apellido = datos[3];
      String email = datos[4];
      LocalDate fechaColaboracion = LocalDate.parse(datos[5], DATE_FORMATTER);
      String formaColaboracion = datos[6];
      Integer cantidad = Integer.parseInt(datos[7]);

      ColaboradorHumano colaboradorExistente = RepositorioColaboradores.getInstance().buscarHumanoPorDocumento(documento);

      if (colaboradorExistente == null) {
        List<MedioContacto> mediosContacto = new ArrayList<>();
        MedioContacto medioContacto = new ContactoMail();
        medioContacto.setContenido(email);
        mediosContacto.add(medioContacto);
        RepositorioMediosDeContacto.getInstance().persistir(medioContacto);

        colaboradorExistente = new ColaboradorHumano("CSV 123", documento, tipoDoc, nombre + apellido, null, mediosContacto, null);

        RepositorioColaboradores.getInstance().persistir(colaboradorExistente);
      }

      List<Colaboracion> colaboraciones = crearColaboracion(formaColaboracion, cantidad, fechaColaboracion, colaboradorExistente);

      for (Colaboracion colaboracion : colaboraciones) {
        RepositorioColaboraciones.getInstance().persistir(colaboracion);
      }

      return colaboradorExistente;
    }

    return null;
  }


  private List<Colaboracion> crearColaboracion(String formaColaboracion, Integer cantidad, LocalDate fechaColaboracion, ColaboradorHumano colaboradorHumano) {
    List<Colaboracion> colaboraciones = new ArrayList<>();

    switch (formaColaboracion.toUpperCase()) {
      case "DINERO" -> {
        for (int i = 0; i < cantidad; i++) {
          DonarDinero donarDinero = new DonarDinero(fechaColaboracion, colaboradorHumano);
          colaboraciones.add(donarDinero);
        }
      }
      case "DONACION_VIANDAS" -> {
        for (int i = 0; i < cantidad; i++) {
          DonarVianda donarVianda = new DonarVianda(fechaColaboracion, colaboradorHumano);
          colaboraciones.add(donarVianda);
        }
      }
      case "REDISTRIBUCION_VIANDAS" -> {
        for (int i = 0; i < cantidad; i++) {
          DistribuirVianda distribuirVianda = new DistribuirVianda(fechaColaboracion, colaboradorHumano);
          colaboraciones.add(distribuirVianda);
        }
      }
      case "ENTREGA_TARJETAS" -> {
        for (int i = 0; i < cantidad; i++) {
          RegistroDePersonaVulnerable registroDePersonaVulnerable = new RegistroDePersonaVulnerable(fechaColaboracion, colaboradorHumano);
          colaboraciones.add(registroDePersonaVulnerable);
        }
      }
      default -> throw new IllegalArgumentException("Tipo de colaboración desconocido: " + formaColaboracion);
    }

    return colaboraciones;
  }
}
