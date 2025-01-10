/*package db.repositorios;

import dominio.entidades.tarjeta.EstadoTarjeta;
import dominio.entidades.tarjeta.TarjetaColaborador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class RepositorioTarjetasTests {

  private Tarjeta tarjeta;
  private RepositorioTarjetas repositorioTarjetas;

  @BeforeEach
  void init() {
    this.tarjeta = new TarjetaColaborador();
    this.repositorioTarjetas = RepositorioTarjetas.getInstance();

    tarjeta.setEstadoTarjeta(EstadoTarjeta.PERDIDA);
    tarjeta.setCodigoAlfaNumerico("02317");

  }

  public void tarjetaSePersiste() {

    repositorioTarjetas.persistir(tarjeta);
    Tarjeta otraTarjeta = repositorioTarjetas.buscarPorId(1);
    Assertions.assertEquals(tarjeta, otraTarjeta);
  }
}

 */