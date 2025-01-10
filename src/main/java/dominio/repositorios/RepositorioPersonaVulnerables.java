package dominio.repositorios;

import dominio.entidades.personasVulnerables.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioPersonaVulnerables implements WithSimplePersistenceUnit {

  private static final RepositorioPersonaVulnerables INSTANCE = new RepositorioPersonaVulnerables();
  private RepositorioPersonaVulnerables() {}
  public static RepositorioPersonaVulnerables getInstance() {return INSTANCE;}

  public void persistir(PersonaVulnerable personaVulnerable) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(personaVulnerable);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<PersonaVulnerable> buscar(){
    return entityManager().createQuery("from PersonaVulnerable").getResultList();
  }

  public void eliminar(PersonaVulnerable personaVulnerable) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(personaVulnerable);
    tx.commit();
  }

  public PersonaVulnerable buscarPorId(long id) {
    return entityManager().find(PersonaVulnerable.class, id);
  }
}