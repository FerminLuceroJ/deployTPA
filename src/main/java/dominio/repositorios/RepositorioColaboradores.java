package dominio.repositorios;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.persona.colaborador.ColaboradorJuridico;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;

public class RepositorioColaboradores implements WithSimplePersistenceUnit {
  private static final RepositorioColaboradores INSTANCE = new RepositorioColaboradores();
  private RepositorioColaboradores() {}
  public static RepositorioColaboradores getInstance() {
    return INSTANCE;
  }

  public void persistir(Colaborador colaborador) {
    EntityTransaction tx = entityManager().getTransaction();
    entityManager().persist(colaborador);
  }

  @SuppressWarnings("unchecked")
  public List<Colaborador> buscar() {
    return (List<Colaborador>) entityManager().createQuery("from Colaborador").getResultList();
  }

  public void eliminar(Colaborador colaborador) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(colaborador);
    tx.commit();
  }

  public Colaborador buscarPorId(long id) {
    return entityManager().find(Colaborador.class, id);
  }

  public ColaboradorHumano buscarHumanoPorId(long id) {
    return entityManager().find(ColaboradorHumano.class, id);
  }

  public ColaboradorJuridico buscarJuridicoPorId(long id) {
    return entityManager().find(ColaboradorJuridico.class, id);
  }

  public ColaboradorHumano buscarHumanoPorDocumento(String documento) {
    List<ColaboradorHumano> resultados = entityManager()
        .createQuery("FROM ColaboradorHumano c WHERE c.numeroDocumento = :documento", ColaboradorHumano.class)
        .setParameter("documento", documento)
        .getResultList();

    return resultados.isEmpty() ? null : resultados.get(0);
  }


  @SuppressWarnings("unchecked")
  public List<Colaborador> buscarPorNombre(String nombre) {
    return entityManager().createQuery("SELECT c from Colaborador c WHERE c.nombreYapellido= :nombre OR c.razonSocial= :nombre").getResultList();
  }
}
