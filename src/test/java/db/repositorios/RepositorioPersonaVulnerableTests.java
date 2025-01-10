package db.repositorios;

import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.repositorios.RepositorioPersonaVulnerables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepositorioPersonaVulnerableTests {

  private PersonaVulnerable personaVulnerable;
  private RepositorioPersonaVulnerables repositorioPersonaVulnerables;

  @BeforeEach
  void init() {
    this.personaVulnerable = new PersonaVulnerable();
    this.repositorioPersonaVulnerables = RepositorioPersonaVulnerables.getInstance();

    personaVulnerable.setNombre("Fermin");
    personaVulnerable.setDireccion("nada");
  }

  @Test
  public void personaVulnerableSePersiste() {

    repositorioPersonaVulnerables.persistir(personaVulnerable);
    PersonaVulnerable otraPersona= repositorioPersonaVulnerables.buscarPorId(1);
    Assertions.assertEquals(personaVulnerable, otraPersona);
  }


}