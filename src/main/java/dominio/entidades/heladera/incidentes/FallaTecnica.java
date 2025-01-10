package dominio.entidades.heladera.incidentes;

import dominio.entidades.heladera.Heladera;
import dominio.imageLoader.ImageLoader;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.awt.image.BufferedImage;
import javax.persistence.Transient;

@Entity
@Setter
@Getter
@DiscriminatorValue("Tecnica")
public class FallaTecnica extends Incidente {

  @Column(nullable = true)
  private String descripcion;
  @Column(nullable = true)
  private String imagePath;


  public FallaTecnica() {}

  public FallaTecnica(Heladera heladera, String descripcion, String imagePath) {
    super(heladera);
    this.descripcion = descripcion;
    this.imagePath = imagePath;
  }

}
