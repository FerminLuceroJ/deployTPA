package db.repositorios;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import dominio.repositorios.RepositorioColaboraciones;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RepositorioColaboracionesTests {

  private ColaboradorHumano colaboradorHumano;
  private Colaboracion colaboracion;
  private RepositorioColaboraciones repositorioColaboraciones;

  @BeforeEach
  void init() {
    this.colaboradorHumano = new ColaboradorHumano(
        "Corrientes 555",
        "12345678",
        "DNI",
        "Juan Perez",
        null,
        new ArrayList<MedioContacto>(),
        new ContactoWPP()
    );
    this.repositorioColaboraciones = RepositorioColaboraciones.getInstance();
  }

  @Test
  void sePersisteDonarDinero() {
    this.colaboracion = new DonarDinero();
    repositorioColaboraciones.persistir(colaboracion);
    Colaboracion colaboracionDB = repositorioColaboraciones.buscarPorId(1);
    Assertions.assertEquals(colaboracion, colaboracionDB);
  }

  @Test
  void sePersisteDonarVianda() {
    this.colaboracion = new DonarVianda();
    repositorioColaboraciones.persistir(colaboracion);
    Colaboracion colaboracionDB = repositorioColaboraciones.buscarPorId(2);
    Assertions.assertEquals(colaboracion, colaboracionDB);
  }

  @Test
  void sePersisteHacerseCargoHeladera() {
    this.colaboracion = new HacerseCargoHeladera();
    repositorioColaboraciones.persistir(colaboracion);
    Colaboracion colaboracionDB = repositorioColaboraciones.buscarPorId(2);
    Assertions.assertEquals(colaboracion, colaboracionDB);
  }

  @Test
  void sePersisteRegistroPersonaVulnerable() {
    this.colaboracion = new RegistroDePersonaVulnerable();
    repositorioColaboraciones.persistir(colaboracion);
    Colaboracion colaboracionDB = repositorioColaboraciones.buscarPorId(2);
    Assertions.assertEquals(colaboracion, colaboracionDB);
  }

  @Test
  void sePersisteDristribuirVianda() {
    this.colaboracion = new DistribuirVianda();
    repositorioColaboraciones.persistir(colaboracion);
    Colaboracion colaboracionDB = repositorioColaboraciones.buscarPorId(2);
    Assertions.assertEquals(colaboracion, colaboracionDB);
  }


}
