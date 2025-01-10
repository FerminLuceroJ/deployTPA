/*package entidades;

import dominio.entidades.colaborador.ColaboradorHumano;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColaboradorCSVLoaderTest {

  private ControladorColaborador controladorColaborador;

  @BeforeEach
  public void setUp() {
    controladorColaborador = new ControladorColaborador();
  }

  @Test
  public void testCargarColaboradoresDesdeCSV() {
    controladorColaborador.cargarColaboradoresDesdeCSV("Ejemplo.csv");
    List<ColaboradorHumano> colaboradores = controladorColaborador.getColaboradores();
    assertNotNull(colaboradores);
    assertEquals(2, colaboradores.size());

    ColaboradorHumano juanPerez = colaboradores.get(0);
    assertEquals(5, juanPerez.getColaboracionesRealizadas().size());

    ColaboradorHumano anaGomez = colaboradores.get(1);
    assertEquals(10, anaGomez.getColaboracionesRealizadas().size());
  }

  @Test
  public void testColaboradorUnico() {
    controladorColaborador.cargarColaboradoresDesdeCSV("Ejemplo.csv");

    long count = controladorColaborador.getColaboradores().stream()
        .filter(c -> c.getNumeroDocumento().equals("12345678"))
        .count();
    assertEquals(1, count);
  }

  @Test
  public void testColaboradorExistente() {
    ColaboradorHumano nuevoColaborador = new ColaboradorHumano(null,
        "12345678", "DNI", "Perez",
        "Juan", null, new ArrayList<>(), null);
    controladorColaborador.registrarColaborador(nuevoColaborador);

    int sizeBeforeLoad = controladorColaborador.getColaboradores().size();
    controladorColaborador.cargarColaboradoresDesdeCSV("src/main/resources/Ejemplo.csv");
    int sizeAfterLoad = controladorColaborador.getColaboradores().size();

    assertEquals(sizeBeforeLoad, sizeAfterLoad);
  }

  @Test
  public void testArchivoCSVInexistente() {
    assertThrows(RuntimeException.class, () -> {
      controladorColaborador.cargarColaboradoresDesdeCSV("src/main/resources/ArchivoInexistente.csv");
    });
  }

  @Test
  public void testArchivoCSVConFormatoIncorrecto() {
    assertThrows(RuntimeException.class, () -> {
      controladorColaborador.cargarColaboradoresDesdeCSV("src/main/resources/ArchivoFormatoIncorrecto.csv");
    });
  }
} */