package dominio.entidades.persona.autentificacion;

import dominio.entidades.persona.colaborador.Colaborador;
import dominio.entidades.persona.colaborador.ColaboradorHumano;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@Entity
public class Usuario {

  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne
  @JoinColumn(name = "id_colaborador", referencedColumnName = "id")
  private Colaborador colaborador;
  @Column
  private String email;
  @Column
  private String password;
  @Column
  @Enumerated(EnumType.STRING)
  private Rol rol;

  public Usuario() {}

  public Usuario(String email, String pass, Rol rol) {
    this.email = email;
    this.password = pass;
    this.rol = rol;
  }

  public Usuario(String email, String pass) {
    this.email = email;
    this.password = pass;
  }

  public boolean esAdmin() {
    return (rol == Rol.ADMIN_PLATAFORMA);
  }

  public boolean esHumano() {
    return (rol == Rol.COLABORADOR_HUMANO);
  }

  public boolean esJuridico() {
    return (rol == Rol.COLABORADOR_JURIDICO);
  }

}

