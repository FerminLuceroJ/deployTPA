package dominio.repositorios;

import dominio.entidades.tarjeta.TarjetaColaborador;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.EntityTransaction;

public class RepositorioTarjetasPersVulnerable implements WithSimplePersistenceUnit {

  private static final RepositorioTarjetasPersVulnerable INSTANCE = new RepositorioTarjetasPersVulnerable();
  private RepositorioTarjetasPersVulnerable() {}
  public static RepositorioTarjetasPersVulnerable getInstance() {return INSTANCE;}

  @SuppressWarnings("unchecked")
  public List<TarjetaPersonaVulnerable> buscar(){
    return entityManager().createQuery("from TarjetaPersonaVulnerable ").getResultList();
  }

  public void persistir(TarjetaPersonaVulnerable tarjeta) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(tarjeta);
    tx.commit();
  }

  public void eliminar(TarjetaPersonaVulnerable tarjeta) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(tarjeta);
    tx.commit();
  }

  public TarjetaPersonaVulnerable buscarPorId(long id) {
    return entityManager().find(TarjetaPersonaVulnerable.class, id);
  }


}
