package dominio.repositorios;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.reportes.Reporte;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioReportes implements WithSimplePersistenceUnit {

  private static final RepositorioReportes INSTANCE = new RepositorioReportes();
  private RepositorioReportes() {}
  public static RepositorioReportes getInstance() {return INSTANCE;}

  public List<Reporte> buscar(){
    return entityManager().createQuery("from Reporte", Reporte.class).getResultList();
  }

  public List<Reporte> buscarPorIdColaborador(long idColaborador){
    return entityManager().createQuery("SELECT r FROM Reporte r WHERE r.colaborador.id=:idColaborador", Reporte.class).setParameter("idColaborador", idColaborador).getResultList();
  }

  public Reporte buscarPorIdReporte(long idReporte){
    return entityManager().find(Reporte.class, idReporte);
  }

  public void persistir(Reporte reporte){
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(reporte);
    tx.commit();
  }
}
