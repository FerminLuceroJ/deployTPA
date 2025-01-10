package dominio.entidades.colaboracion;

import dominio.entidades.persona.colaborador.Colaborador;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Colaboracion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_colaboracion")
    protected LocalDate fechaColaboracion;
    @Column(name = "coeficiente")
    protected double coeficiente;
    @ManyToOne
    @JoinColumn(name = "id_donante", referencedColumnName = "id")
    protected Colaborador colaborador;


    public abstract void aplicarColaboracion();
    public abstract double calcularPuntaje();
    public abstract double actualizarCoeficientes(String path);

    public String getFechaColaboracionFormateada() {
        return fechaColaboracion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
