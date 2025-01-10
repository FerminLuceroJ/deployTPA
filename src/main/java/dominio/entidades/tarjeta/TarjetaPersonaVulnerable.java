package dominio.entidades.tarjeta;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.personasVulnerables.PersonaVulnerable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
@Entity
@DiscriminatorValue("PV")
public class TarjetaPersonaVulnerable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String codigoAlfaNumerico;
  @Enumerated(EnumType.STRING)
  private EstadoTarjeta estadoTarjeta;
  @OneToMany
  @JoinColumn(name = "id_uso_tarjeta", referencedColumnName = "id")
  private List<UsoDeTarjeta> usosDeTarjeta;
  @ManyToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private Colaborador colaboradorQueEntrego;

  public TarjetaPersonaVulnerable(String codigoAlfaNumerico) {
    if(codigoAlfaNumerico == null){
      throw new RuntimeException("Se debe ingresar el codigo alfa numerico");
    }
    this.estadoTarjeta = EstadoTarjeta.PENDIENTE_ENTREGA;
    this.codigoAlfaNumerico = codigoAlfaNumerico;
  }

  public TarjetaPersonaVulnerable() {}

  public void entregarAPersonaVulnerable(PersonaVulnerable personaDuenia){
    if(personaDuenia == null){
      throw new RuntimeException("Se debe ingresar la persona dueña");
    }
    this.estadoTarjeta = EstadoTarjeta.ENTREGADA;
  }
  
  public void tarjetaPerdida(){
    this.estadoTarjeta = EstadoTarjeta.PERDIDA;
  }

  public void usarTarjeta(Heladera heladera){
    if(estadoTarjeta != EstadoTarjeta.PERDIDA){
      usosDeTarjeta.add(new UsoDeTarjeta(heladera));
    }
  }

  public Integer mesesActiva() {

    if(usosDeTarjeta.isEmpty()) {
      return 0;
    }
      LocalDate fechaInicial = usosDeTarjeta.get(0).getFechaDeUso();
      long diasEntre = ChronoUnit.DAYS.between(fechaInicial, LocalDate.now());

      return (int) Math.ceil(diasEntre / 31.0);

  }


}
