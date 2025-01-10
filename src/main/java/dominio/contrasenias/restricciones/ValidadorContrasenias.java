package dominio.contrasenias.restricciones;

import dominio.contrasenias.excepciones.ContraseniaInvalidaException;
import dominio.contrasenias.restricciones.expresionRegular.ContieneMayusculaER;
import dominio.contrasenias.restricciones.expresionRegular.ContieneMinisculaEP;
import dominio.contrasenias.restricciones.expresionRegular.ContieneNumeroER;
import dominio.contrasenias.restricciones.expresionRegular.ContieneSignoDePuntuacionER;
import dominio.contrasenias.restricciones.longitud.LongitudMaxima;
import dominio.contrasenias.restricciones.longitud.LongitudMinima;
import dominio.entidades.persona.autentificacion.Usuario;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidadorContrasenias {

  private final Set<Restriccion> restricciones;

  // En este momento, cuando se instancia, ya se le cargan automaticamente las restricciones que tiene nuestro sistema
  // Podria modificarse el constructor y cargarlas en el momento de instanciacion
  public ValidadorContrasenias() {
    this.restricciones = new HashSet<>();
    this.cargarRestriccionesActuales();
  }

  public void esValidaContrasenia(Usuario usuario) {
    List<String> errores = restricciones.stream()
        .filter(restriccion -> !restriccion.esValidaContrasenia(usuario))
        .map(Restriccion::getMensajeError)
        .collect(Collectors.toList());
    if (!errores.isEmpty()) {
      throw new ContraseniaInvalidaException(String.join(", ", errores));
    }
  }

  // Quizá estaria bueno tener una clase constructor para que esta logica no esté acá
  private void cargarRestriccionesActuales() {
    Restriccion listaContraseniasDebiles = new ListaDeContraseniasDebiles();
    Restriccion longitudMaxima = new LongitudMaxima();
    Restriccion longitudMinima = new LongitudMinima();
    Restriccion contieneMayusculas = new ContieneMayusculaER();
    Restriccion contieneMinuscula = new ContieneMinisculaEP();
    Restriccion contieneNumero = new ContieneNumeroER();
    Restriccion contieneSignoPuntuacion = new ContieneSignoDePuntuacionER();
    Collections.addAll(this.restricciones,
        listaContraseniasDebiles,
        longitudMaxima,
        longitudMinima,
        contieneMayusculas,
        contieneMinuscula,
        contieneNumero,
        contieneSignoPuntuacion
    );
  }
}
