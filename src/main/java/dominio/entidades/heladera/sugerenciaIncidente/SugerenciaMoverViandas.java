package dominio.entidades.heladera.sugerenciaIncidente;

import dominio.entidades.colaboracion.DistribuirVianda;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import dominio.entidades.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Getter @Setter
public class SugerenciaMoverViandas implements SugerenciaIncidente{

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  @JoinColumn(name = "id_heladera_dañada", referencedColumnName = "id")
  private Heladera heladeraDaniada;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "sugerencia_heladeras", joinColumns = @JoinColumn(name = "id_sugerencia"), inverseJoinColumns = @JoinColumn(name = "id_heladera"))
  private List<Heladera> heladerasParaTransferir;

  public SugerenciaMoverViandas() {}

  public SugerenciaMoverViandas(Heladera heladeraDaniada,List<Heladera> heladerasParaTransferir) {
    this.heladeraDaniada = heladeraDaniada;
    this.heladerasParaTransferir = heladerasParaTransferir;
  }

  @Override
  public void aceptar(ColaboradorHumano colaborador) {
    heladerasParaTransferir.forEach(heladera -> {
      DistribuirVianda distribuirVianda = new DistribuirVianda(heladera.capacidadRestante(), "Incidente en otra heladera", heladeraDaniada, heladera);
      colaborador.realizarColaboracion(distribuirVianda);});
  }

}
