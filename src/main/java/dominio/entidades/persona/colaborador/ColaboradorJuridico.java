package dominio.entidades.persona.colaborador;

import dominio.entidades.colaboracion.Colaboracion;
import dominio.localizacion.Localizacion;
import dominio.notificador.mediosDeContacto.MedioContacto;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ColaboradorJuridico extends Colaborador {


  @Enumerated(EnumType.STRING)
  private TipoJuridico tipoJuridico;
  @Enumerated(EnumType.STRING)
  private Rubro rubro;;

  public ColaboradorJuridico() {}

  public ColaboradorJuridico(
      String razonSocial,
      Localizacion localizacion,
      TipoJuridico tipoJuridico,
      Rubro rubro,
      List<MedioContacto> mediosDeContacto
  ) {
    if(razonSocial == null){
      throw new RuntimeException("Se debe ingresar la razon Social");
    }
    if(tipoJuridico == null){
      throw new RuntimeException("Se debe ingresar el tipo Juridico");
    }
    if(rubro == null){
      throw new RuntimeException("Se debe ingresar el rubro");
    }

    this.razonSocial = razonSocial;
    this.localizacion = localizacion;
    this.tipoJuridico = tipoJuridico;
    this.rubro = rubro;
  }

  @Override
  public String getTipo() {return "Juridico";}

  @Override
  public Boolean esJuridico(){return true;}

  @Override
  public void realizarColaboracion(Colaboracion colaboracion) {
    colaboracion.aplicarColaboracion();
    //this.getColaboracionesRealizadas().add(colaboracion);
  }

}
