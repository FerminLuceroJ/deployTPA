package dominio.repositorios;

import dominio.entidades.tarjeta.TarjetaColaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioTarjetasColaborador implements WithSimplePersistenceUnit {

  private static final RepositorioTarjetasColaborador INSTANCE = new RepositorioTarjetasColaborador();
  private RepositorioTarjetasColaborador() {}
  public static RepositorioTarjetasColaborador getInstance() {return INSTANCE;}

  public void persistir(TarjetaColaborador tarjeta) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(tarjeta);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<TarjetaColaborador> buscar(){
    return entityManager().createQuery("from TarjetaColaborador").getResultList();
  }

  public void eliminar(TarjetaColaborador tarjeta) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(tarjeta);
    tx.commit();
  }

  public TarjetaColaborador buscarPorId(long id) {
    return entityManager().find(TarjetaColaborador.class, id);
  }
}
