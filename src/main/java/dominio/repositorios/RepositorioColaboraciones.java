package dominio.repositorios;

import com.mysql.cj.xdevapi.SessionFactory;
import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.persona.colaborador.Colaborador;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityTransaction;
import java.util.List;
import org.hsqldb.Session;

public class RepositorioColaboraciones implements WithSimplePersistenceUnit {
  private static final RepositorioColaboraciones INSTANCE = new RepositorioColaboraciones();
  private RepositorioColaboraciones() {}
  public static RepositorioColaboraciones getInstance() {
    return INSTANCE;
  }

  public void persistir(Colaboracion colaboracion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(colaboracion);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<Colaboracion> buscar(){
    List<Colaboracion> colaboraciones = (List<Colaboracion>) entityManager().createQuery("from Colaboracion").getResultList();

    colaboraciones.forEach(colaboracion -> entityManager().refresh(colaboracion));

    return colaboraciones;

  }

  @SuppressWarnings("unchecked")
  public List<Colaboracion> buscarPorTipo(String tipo){
    try{
      Class<?> clase = Class.forName("dominio.entidades.colaboracion." + tipo);
      return entityManager().createQuery("SELECT c FROM Colaboracion c WHERE TYPE(c)= :tipo", Colaboracion.class).setParameter("tipo", clase).getResultList();
    } catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }

  public List<Colaboracion> buscarPorIdColaborador(Long idColaborador){

    List<DonarDinero> donacionesDinero = entityManager().createQuery(
            "SELECT d FROM DonarDinero d WHERE d.colaborador.id = :idColaborador", DonarDinero.class)
        .setParameter("idColaborador", idColaborador)
        .getResultList();

    List<DistribuirVianda> distribucionesVianda = entityManager().createQuery(
            "SELECT dv FROM DistribuirVianda dv WHERE dv.colaborador.id = :idColaborador", DistribuirVianda.class)
        .setParameter("idColaborador", idColaborador)
        .getResultList();

    List<DonarVianda> donacionesVianda = entityManager().createQuery(
            "SELECT o FROM DonarVianda o WHERE o.colaborador.id = :idColaborador", DonarVianda.class)
        .setParameter("idColaborador", idColaborador)
        .getResultList();

    List<HacerseCargoHeladera> heladerasACargo = entityManager().createQuery(
            "SELECT oi FROM HacerseCargoHeladera oi WHERE oi.colaborador.id = :idColaborador", HacerseCargoHeladera.class)
        .setParameter("idColaborador", idColaborador)
        .getResultList();

    List<RegistroDePersonaVulnerable> registroDePersonaVulnerables = entityManager().createQuery(
            "SELECT os FROM RegistroDePersonaVulnerable os WHERE os.colaborador.id = :idColaborador", RegistroDePersonaVulnerable.class)
        .setParameter("idColaborador", idColaborador)
        .getResultList();

    List<Colaboracion> colaboraciones = new ArrayList<>();
    colaboraciones.addAll(donacionesDinero);
    colaboraciones.addAll(distribucionesVianda);
    colaboraciones.addAll(donacionesVianda);
    colaboraciones.addAll(heladerasACargo);
    colaboraciones.addAll(registroDePersonaVulnerables);

    return colaboraciones;
  }

  public void eliminar(Colaboracion colaboracion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(colaboracion);
    tx.commit();
  }

public Colaboracion buscarPorIdColaboracionFromColaborador(long idColaboracion, long idColaborador) {
  Colaboracion colaboracion = entityManager().find(Colaboracion.class, idColaboracion);
  if (colaboracion != null && colaboracion.getColaborador().getId() == idColaborador) {
    return colaboracion;
  }
  return null;
}

  public Colaboracion buscarPorId(long id) {
    return entityManager().find(Colaboracion.class, id);
  }

  public void update(Colaboracion colaboracion){
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().merge(colaboracion);
    tx.commit();
  }
}
