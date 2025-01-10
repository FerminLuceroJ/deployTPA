/*package db.repositorios;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepositorioColaboradoresHumanosTests {

  private ColaboradorHumano colaboradorHumano;
  private RepositorioColaboradoresHumanos repositorioColaboladores;

  @BeforeEach
  void init() {
    this.colaboradorHumano = new ColaboradorHumano();
    this.repositorioColaboladores = RepositorioColaboradoresHumanos.getInstance();

    colaboradorHumano.setNombreYapellido("Fermin Lucero");
    colaboradorHumano.setTarjetaColaborador(null);
    colaboradorHumano.setNotificaciones(null);
  }

  @Test
  public void colaboradorSePersiste() {

    repositorioColaboladores.persistir(colaboradorHumano);
    Long id = (long)1;
    ColaboradorHumano otroColaborador = repositorioColaboladores.buscarPorId(id);
    Assertions.assertEquals(colaboradorHumano, otroColaborador);
  }

}

 */
