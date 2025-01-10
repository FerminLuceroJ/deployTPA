package dominio.localizacion;

import dominio.entidades.heladera.Heladera;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@Setter
@Embeddable
public class Localizacion {

    @Column(name = "latitud")
    private float latitud;
    @Column(name = "longitud")
    private float longitud;

    public Localizacion(float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Localizacion() {}

    public Double distanciaEntrePuntos(Localizacion unaLocalizacion) {

        double radioTierra = 6371.0;

        double latitud1 = Math.toRadians(latitud);
        double longitud1 = Math.toRadians(longitud);
        double latitud2 = Math.toRadians(unaLocalizacion.getLatitud());
        double longitud2 = Math.toRadians(unaLocalizacion.getLongitud());

        double deltaSigma = Math.acos(Math.sin(latitud1) * Math.sin(latitud2) +
            Math.cos(latitud1) * Math.cos(latitud2) * Math.cos(longitud2 - longitud1));

        return radioTierra * deltaSigma;
    }
}
