package dominio.entidades.colaboracion;

import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import dominio.lectorArchivos.lecturaCSV.CargaCoeficientes;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter @Setter
@Entity
public class RegistroDePersonaVulnerable extends Colaboracion{

    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "id_personaVulnerable", referencedColumnName = "id")
    private PersonaVulnerable personaVulnerable;

  public RegistroDePersonaVulnerable(
      PersonaVulnerable personaVulnerable,
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable
  ){
    this.personaVulnerable = personaVulnerable;
    this.fechaColaboracion = LocalDate.now();
    this.coeficiente = this.actualizarCoeficientes("archivos/CoeficientesColaboraciones.csv");
  }

  public RegistroDePersonaVulnerable(LocalDate fechaColaboracion, ColaboradorHumano colaboradorHumano) {
    this.fechaColaboracion = fechaColaboracion;
    this.colaborador = colaboradorHumano;
  }

  public RegistroDePersonaVulnerable() {}

  @Override
  public void aplicarColaboracion() {

  }

  public Integer mesesActiva() {
    return personaVulnerable.getTarjetaPersonaVulnerable().mesesActiva();
  }

  public Integer cantidadUsos() {
    return personaVulnerable.getTarjetaPersonaVulnerable().getUsosDeTarjeta().size();
  }

  @Override
  public double calcularPuntaje() {
    return (this.mesesActiva() * this.cantidadUsos() * coeficiente);
  }

  @Override
  public double actualizarCoeficientes(String path) {
    path = "archivos/CoeficientesColaboraciones.csv";
    CargaCoeficientes cargaCoeficientes = new CargaCoeficientes();
    cargaCoeficientes.cargarCoeficientes(path);

    return cargaCoeficientes.getCoeficiente("ENTREGA_TARJETAS");
  }


  }

