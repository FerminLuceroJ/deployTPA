package db.repositorios;

import dominio.entidades.heladera.incidentes.FallaTecnica;
import dominio.entidades.heladera.incidentes.Incidente;
import dominio.repositorios.RepositorioIncidente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class RepositorioIncidentesTests {

  private Incidente incidente;
  private RepositorioIncidente repositorioIncidente;

  @BeforeEach
  void init() {
    this.incidente = new FallaTecnica();
    this.repositorioIncidente = RepositorioIncidente.getInstance();

    incidente.setHeladera(null);
  }

  public void incidenteSePersiste() {

    repositorioIncidente.persistir(incidente);
    Incidente otroIncidente = repositorioIncidente.buscarPorId(1);
    Assertions.assertEquals(incidente, otroIncidente);
  }

}