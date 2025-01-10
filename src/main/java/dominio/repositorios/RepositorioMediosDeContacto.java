package dominio.repositorios;

import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.ContactoTelegram;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.Transaction;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class RepositorioMediosDeContacto implements WithSimplePersistenceUnit {

  private static final RepositorioMediosDeContacto INSTANCE = new RepositorioMediosDeContacto();

  private RepositorioMediosDeContacto() {
  }

  public static RepositorioMediosDeContacto getInstance() {
    return INSTANCE;
  }

  public void persistir(MedioContacto medioContacto) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(medioContacto);
    tx.commit();
  }

  public List<MedioContacto> buscar() {
    return entityManager().createQuery("SELECT m FROM MedioContacto m", MedioContacto.class).getResultList();
  }

  public <T extends MedioContacto> T buscarPorIdyTipo(long id, Class<T> tipoContacto) {
    try {
      return (T) entityManager().createQuery(
              "SELECT m FROM MedioContacto m WHERE m.id = :id")
          .setParameter("id", id)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }




}
