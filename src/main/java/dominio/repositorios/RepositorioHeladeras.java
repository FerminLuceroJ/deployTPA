package dominio.repositorios;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.persona.autentificacion.Usuario;
import dominio.localizacion.Localizacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioHeladeras implements WithSimplePersistenceUnit {

  private static final RepositorioHeladeras INSTANCE = new RepositorioHeladeras();
  public RepositorioHeladeras() {}
  public static RepositorioHeladeras getInstance() {return INSTANCE;}

  public void persistir(Heladera heladera) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(heladera);
    tx.commit();
  }

  public List<Heladera> buscar(){
    return entityManager().createQuery("from Heladera").getResultList();
  }

  public void eliminar(Heladera heladera) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(heladera);
    tx.commit();
  }

  public Heladera buscarPorId(long id) {
    return entityManager().find(Heladera.class, id);
  }

  public List<Heladera> obtenerHeladerasCercanas(Localizacion localizacion) {

    List<Heladera> heladeras = this.buscar();

    List<Heladera> heladerasCercanas = heladeras.stream()
        .sorted((h1, h2) -> {
          double distancia1 = h1.getLocalizacion().distanciaEntrePuntos(localizacion);
          double distancia2 = h2.getLocalizacion().distanciaEntrePuntos(localizacion);
          return Double.compare(distancia1, distancia2);
        })
        .limit(5) // Limitar a las 5 más cercanas
        .toList();

    return heladerasCercanas;
  }

  public List<Heladera> buscarPorNombreSignificativo(String nombre) {
    List<Heladera> heladeras = entityManager().createQuery
        ("FROM Heladera h WHERE h.nombreSignificativo = :nombre", Heladera.class).getResultList();

    return heladeras;
  }

  public Heladera buscarPorSerialNumber(String serialNumber) {
    return entityManager().find(Heladera.class, serialNumber);
  }

  public void modificar(Heladera heladera) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().merge(heladera);
    tx.commit();
  }
}
