package dominio.repositorios;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.sugerenciaIncidente.SugerenciaMoverViandas;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioSugerencias implements WithSimplePersistenceUnit {

  private static final RepositorioSugerencias INSTANCE = new RepositorioSugerencias();
  public RepositorioSugerencias() {}
  public static RepositorioSugerencias getInstance() {return INSTANCE;}

  public void persistir(SugerenciaMoverViandas sugerencia) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(sugerencia);
    tx.commit();
  }

  public void eliminar(SugerenciaMoverViandas sugerencia) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(sugerencia);
    tx.commit();
  }

  public SugerenciaMoverViandas buscarPorId(Long id) {
    return entityManager().find(SugerenciaMoverViandas.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Heladera> heladerasDestinoDeSugerencia(Long id) {
    return entityManager().createQuery("SELECT s.heladerasParaTransferir From SugerenciaMoverViandas s WHERE s.id = id").getResultList();
  }
}
