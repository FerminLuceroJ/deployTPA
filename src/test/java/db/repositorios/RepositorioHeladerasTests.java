package db.repositorios;

import dominio.entidades.heladera.Heladera;
import dominio.repositorios.RepositorioHeladeras;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class RepositorioHeladerasTests {

  private Heladera heladera;

  @BeforeEach
  void init() {
    this.heladera = new Heladera();
    heladera.setCantidadViandas(19);
    heladera.setDireccion("nada");
  }

  public void heladeraSePersiste() {
    RepositorioHeladeras.getInstance().persistir(heladera);
    Heladera otraHeladera = RepositorioHeladeras.getInstance().buscarPorId(1);
    Assertions.assertEquals(heladera, otraHeladera);
  }


}
