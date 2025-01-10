/*package entidades;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.entidades.colaboracion.Comida;
import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.colaboracion.DonarDinero;
import dominio.entidades.colaboracion.DonarVianda;
import dominio.entidades.colaboracion.Frecuencia;
import dominio.entidades.colaboracion.HacerseCargoHeladera;
import dominio.entidades.colaboracion.RegistroDePersonaVulnerable;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import dominio.entidades.heladera.incidentes.suscripciones.TipoSuscripcion;
import dominio.localizacion.Localizacion;
import dominio.entidades.heladera.aperturas.AdapterControladorAcceso;
import dominio.entidades.heladera.sensorPeso.AdapterPeso;
import dominio.entidades.heladera.sensorTemperatura.AdapterTemperatura;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.entidades.personasVulnerables.TipoDocumento;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import dominio.entidades.tarjeta.UsoDeTarjeta;
import dominio.entidades.vianda.Estado;
import dominio.entidades.vianda.Vianda;
import dominio.notificador.mediosDeContacto.ContactoMail;
import dominio.notificador.mediosDeContacto.ContactoWPP;
import dominio.notificador.mediosDeContacto.MedioContacto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.reportes.CalculadorReporte;
import dominio.reportes.Reporte;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ReporteTest {

  private Reporte reporte;
  private PersonaVulnerable personaVulnerable;
  private ColaboradorHumano colaboradorHumano;
  CalculadorReporte calculadorReporte = new CalculadorReporte();
  LocalDate fechaHoy;
  LocalDate fechaFutura;
  LocalDate fechaPasada;
  AdapterPeso adapterPeso;
  AdapterTemperatura adapterTemperatura;
  AdapterControladorAcceso adapterControladorAcceso;
  private Heladera heladera;

  @BeforeEach
  void setUp() {
    MedioContacto medioContacto = new ContactoMail();
    List<MedioContacto> mediosDeContacto = List.of(medioContacto);
    colaboradorHumano = new ColaboradorHumano(
        "Corrientes 555",
        "12345678",
        "DNI",
        "Gimenez",
        "Tomas",
        null,
        new ArrayList<MedioContacto>(),
        new ContactoWPP()
    );
    colaboradorHumano = new ColaboradorHumano(
        "Corrientes 555",
        "12345678",
        "DNI",
        "Gimenez",
        "Tomas",
        null,
        new ArrayList<MedioContacto>(),
        new ContactoWPP()
    );

    personaVulnerable = new PersonaVulnerable(
        "Persona",
        LocalDate.now().minusYears(15),
        true,
        "Rivadavia 2",
        TipoDocumento.DNI,
        12345,
        2,
        colaboradorHumano,
        null
    );

    // Set de Fechas
    fechaHoy = LocalDate.now();
    fechaFutura = LocalDate.now().plusDays(10);
    fechaPasada = LocalDate.now().minusDays(10);

    // Mock de adapters
    adapterTemperatura = mock(AdapterTemperatura.class);
    adapterPeso = mock(AdapterPeso.class);
    adapterControladorAcceso = mock(AdapterControladorAcceso.class);

    heladera = mock(Heladera.class);
  }


  @Test
  void inicializarReporte() {
    Reporte reporte = new Reporte(colaboradorHumano, fechaHoy, 10);
    Assertions.assertNotNull(reporte);
  }

  @Test
  void inicializacionReporteContieneColaboradorCorrespondiente() {
    Reporte reporte = new Reporte(colaboradorHumano, fechaHoy, 10);
    Assertions.assertEquals(reporte.getColaborador(), colaboradorHumano);
  }

  @Test
  void calculoDePuntajeCorrectoDonarViandas() {
    // Instancia Heladera
    Heladera heladera = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);

    // Instancias de Viandas
    Vianda vianda1 = new Vianda(fechaFutura, fechaHoy, heladera, 100, 10f, Estado.NO_ENTREGADA, Comida.PERECEDERA, colaboradorHumano);
    Vianda vianda2 = new Vianda(fechaFutura, fechaHoy, heladera, 100, 10f, Estado.NO_ENTREGADA, Comida.NO_PERECEDERA, colaboradorHumano);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion colaboracion1 = new DonarVianda(vianda1,heladera,colaboradorHumano);
    Colaboracion colaboracion2 = new DonarVianda(vianda2, heladera, colaboradorHumano);
    colaboradorHumano.realizarColaboracion(colaboracion1);
    colaboradorHumano.realizarColaboracion(colaboracion2);

    CalculadorReporte calculadorReporte = new CalculadorReporte();
    double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    Assertions.assertEquals(6, puntaje,0.2);
  }

  @Test
  void calculoDePuntajeCorrectoDonarDinero() {
    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion colaboracionDinero = new DonarDinero(50,Frecuencia.DIARIA,colaboradorHumano);
    colaboradorHumano.realizarColaboracion(colaboracionDinero);

    Double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    Assertions.assertEquals(25, puntaje, 0.2);
  }

  @Test
  void calculoDePuntajeCorrectoRegistroDePersonaVulnerable() {
    Heladera heladera = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);

    TarjetaPersonaVulnerable unaTarjetaPersonaVulnerable = new TarjetaPersonaVulnerable("12345");
    personaVulnerable.setTarjetaPersonaVulnerable(unaTarjetaPersonaVulnerable);

    unaTarjetaPersonaVulnerable.entregarAPersonaVulnerable(personaVulnerable);
    List<UsoDeTarjeta> usosDeTarjeta = new ArrayList<>();
    UsoDeTarjeta usoDeTarjeta = new UsoDeTarjeta(heladera);
    usoDeTarjeta.setFechaDeUso(LocalDate.now().minusMonths(2));
    UsoDeTarjeta usoDeTarjeta2 = new UsoDeTarjeta(heladera);
    usosDeTarjeta.add(usoDeTarjeta);
    usosDeTarjeta.add(usoDeTarjeta2);
    unaTarjetaPersonaVulnerable.setUsosDeTarjeta(usosDeTarjeta);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion registroDePersonaVulnerable = new RegistroDePersonaVulnerable(
        personaVulnerable,
        unaTarjetaPersonaVulnerable
    );

    colaboradorHumano.realizarColaboracion(registroDePersonaVulnerable);
    double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    Assertions.assertEquals(8, puntaje, 0.2);
  }

  @Test
  void calculoDePuntajeCorrectoHacerseCargoHeladera() {
    // Instancia Heladera
    Heladera heladeraUsadaYActiva = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);
    heladeraUsadaYActiva.setCantidadUsos(5);
    // Crear Colaboraciones y add a ColaboradorHumano
    Colaboracion colaboracionHeladera = new HacerseCargoHeladera(heladeraUsadaYActiva);
    colaboradorHumano.realizarColaboracion(colaboracionHeladera);

    double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    Assertions.assertEquals(25, puntaje, 0.2);
  }

  @Test
  void calculoDePuntajeCorrectoDistribuirVianda() {
    // Instancia Heladera
    Heladera heladeraOrigen = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);
    Heladera heladeraDestino = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion colaboracionDistribuirVianda = new DistribuirVianda(10, "Motivo",heladeraOrigen, heladeraDestino );
    Colaboracion colaboracionDistribuirVianda2 = new DistribuirVianda(30, "Motivo",heladeraOrigen, heladeraDestino );
    Suscripcion suscripcion = new Suscripcion(heladera, TipoSuscripcion.CANTIDAD_DE_VIANDAS, 1);
    colaboradorHumano.suscribirseA(heladera, suscripcion);
    colaboradorHumano.realizarColaboracion(colaboracionDistribuirVianda);
    colaboradorHumano.realizarColaboracion(colaboracionDistribuirVianda2);

    double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    Assertions.assertEquals(40, puntaje, 0.2);
  }

  @Test
  void calculoMultiplesColaboraciones() {
    // Instancia Heladera "1S2A3R4A5S6A"
    Heladera heladera = buildHeladera("1S2A3R4A5S6A", "Rivadavia123", fechaPasada);

    // Instancias de Viandas
    Vianda vianda1 = new Vianda(fechaFutura, fechaHoy, heladera, 100, 10f, Estado.NO_ENTREGADA, Comida.PERECEDERA, colaboradorHumano);
    Vianda vianda2 = new Vianda(fechaFutura, fechaHoy, heladera, 100, 10f, Estado.NO_ENTREGADA, Comida.NO_PERECEDERA, colaboradorHumano);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion colaboracion1 = new DonarVianda(vianda1,heladera,colaboradorHumano);
    Colaboracion colaboracion2 = new DonarVianda(vianda2, heladera,colaboradorHumano);
    colaboradorHumano.realizarColaboracion(colaboracion1);
    colaboradorHumano.realizarColaboracion(colaboracion2);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion colaboracionDinero = new DonarDinero(50, Frecuencia.DIARIA,colaboradorHumano);
    colaboradorHumano.realizarColaboracion(colaboracionDinero);

    TarjetaPersonaVulnerable unaTarjetaPersonaVulnerable = new TarjetaPersonaVulnerable("12345");
    personaVulnerable.setTarjetaPersonaVulnerable(unaTarjetaPersonaVulnerable);

    unaTarjetaPersonaVulnerable.entregarAPersonaVulnerable(personaVulnerable);
    List<UsoDeTarjeta> usosDeTarjeta = new ArrayList<>();
    UsoDeTarjeta usoDeTarjeta = new UsoDeTarjeta(heladera);
    usoDeTarjeta.setFechaDeUso(LocalDate.now().minusMonths(2));
    UsoDeTarjeta usoDeTarjeta2 = new UsoDeTarjeta(heladera);
    usosDeTarjeta.add(usoDeTarjeta);
    usosDeTarjeta.add(usoDeTarjeta2);
    unaTarjetaPersonaVulnerable.setUsosDeTarjeta(usosDeTarjeta);

    // Crear Colaboraciones y aniadir a ColaboradorHumano
    Colaboracion registroDePersonaVulnerable = new RegistroDePersonaVulnerable(
        personaVulnerable,
        unaTarjetaPersonaVulnerable
    );
    colaboradorHumano.realizarColaboracion(registroDePersonaVulnerable);

    // Instancia Heladera
    Heladera heladeraUsadaYActiva = buildHeladera("123", "Rivadavia123", fechaPasada);
    heladeraUsadaYActiva.setCantidadUsos(5);
    // Crear Colaboraciones y add a ColaboradorHumano
    Colaboracion colaboracionHeladera = new HacerseCargoHeladera(heladeraUsadaYActiva);
    colaboradorHumano.realizarColaboracion(colaboracionHeladera);

    double puntaje = calculadorReporte.calcularTotalPuntaje(colaboradorHumano.getColaboracionesRealizadas());
    // (Teniendo en cuenta los tests anteriores) --- 12 + 25 + 8 + 25 = 70
    Assertions.assertEquals(64, puntaje, 0.2);
  }

  public Heladera buildHeladera(String serialNumber, String direccion, LocalDate fecha) {
    return new Heladera(
        serialNumber,
        "Heladera1",
        new Localizacion(2, 2),
        direccion,
        400,
        fecha,
        true,
        new ArrayList<>(),
        4.0,
        8.0,
        adapterTemperatura,
        adapterPeso,
        adapterControladorAcceso,
        null
    );
  }

}
*/