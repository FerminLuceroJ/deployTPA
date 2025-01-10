package dominio.entidades.personasVulnerables;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.heladera.Heladera;
import dominio.entidades.tarjeta.TarjetaPersonaVulnerable;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@Entity
public class PersonaVulnerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "fechaIngreso")
    private LocalDate fechaIngreso;
    @Column(name = "enSituacionDeCalle")
    private Boolean situacionDeCalle;
    @Column(name = "direccion")
    private String direccion;
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
    @Column(name = "numero_documento")
    private Integer numeroDocumento;
    @Column(name = "cantidad_menores_a_Cargo")
    private Integer cantidadMenoresACargo;
    @ManyToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
    private Colaborador colaboradorAsignado;
    @OneToOne
    @JoinColumn(name = "id_tarjeta", referencedColumnName = "id")
    private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

    public PersonaVulnerable(
        String nombre,
        LocalDate fechaNacimiento,
        Boolean situacionDeCalle,
        String direccion,
        TipoDocumento tipoDocumento,
        Integer numeroDocumento,
        Integer cantidadMenoresACargo,
        Colaborador colaboradorAsignado,
        TarjetaPersonaVulnerable tarjetaPersonaVulnerable
    ) {
        if(nombre == null){
            throw new RuntimeException("El nombre es requerido");
        }
        if(fechaNacimiento == null){
            throw new RuntimeException("La fecha de nacimiento es requerida");
        }

        if(tipoDocumento == null){
            throw new RuntimeException("El tipo de documento es requerido");
        }
        if(numeroDocumento == null){
            throw new RuntimeException("El numero de documento es requerido");
        }
        if(situacionDeCalle == null){
            throw new RuntimeException("Es necesario ingresar si se encuentra en situacion de calle");
        }
        if(direccion == null){
            throw new RuntimeException("La direccion es requerida");
        }
        if(cantidadMenoresACargo == null){
            throw new RuntimeException("La cantidad de menores acargo es requerida");
        }
        if(colaboradorAsignado == null){
            throw new RuntimeException("El colaborador es requerido");
        }

        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = LocalDate.now();
        this.situacionDeCalle = situacionDeCalle;
        this.direccion = direccion;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.cantidadMenoresACargo = cantidadMenoresACargo;
        this.colaboradorAsignado = colaboradorAsignado;
        this.tarjetaPersonaVulnerable = tarjetaPersonaVulnerable;
    }

    public PersonaVulnerable() {}

    public Boolean tieneMenoresACargo(){
        return cantidadMenoresACargo > 0;
    }

    public void usarTarjeta(Heladera heladera){
        if(!puedeUsarTarjeta()) throw new RuntimeException("Usos diarios excedidos");
        this.tarjetaPersonaVulnerable.usarTarjeta(heladera);
        heladera.setCantidadUsos(heladera.getCantidadUsos() + 1);
        heladera.sacarVianda();
    }

    public Integer usosDelDia(){
        return tarjetaPersonaVulnerable.getUsosDeTarjeta().stream().filter(uso -> uso.getFechaDeUso().equals(LocalDate.now())).toList().size();
    }

    public Boolean puedeUsarTarjeta() {
        return (this.usosDelDia() <= (getCantidadMenoresACargo()*2 + 4));
    }

    public String getFechaIngresoFormateada() {
        if (fechaIngreso == null) {
            return "Fecha no disponible";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaIngreso.format(formatter);
    }

    public String getFechaNacimientoFormateada() {
        if (fechaNacimiento == null) {
            return "Fecha no disponible";
        }
        return fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

