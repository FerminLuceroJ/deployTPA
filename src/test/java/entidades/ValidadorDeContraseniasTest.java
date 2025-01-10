package entidades;

import dominio.contrasenias.excepciones.ContraseniaInvalidaException;
import dominio.contrasenias.restricciones.ValidadorContrasenias;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import dominio.entidades.persona.autentificacion.Usuario;
import java.util.Arrays;

public class ValidadorDeContraseniasTest {

  private void verificarContraseniaInvalida(String contrasenia, String mensajeEsperado) {
    ValidadorContrasenias validador = new ValidadorContrasenias();
    Usuario usuarioConContrasenia = new Usuario("fermin05", contrasenia, null);

    ContraseniaInvalidaException e = Assertions.assertThrows(ContraseniaInvalidaException.class, () -> validador.esValidaContrasenia(usuarioConContrasenia));

    Assertions.assertTrue(e.getMessage().contains(mensajeEsperado));
  }

  @Test
  public void contraseniasValidasTest() {
    ValidadorContrasenias validador = new ValidadorContrasenias();

    Usuario usuarioContraseniaSeguraUno = new Usuario("fermin05", "un4contraS3gur4!", null);
    Usuario usuarioContraseniaSeguraDos = new Usuario("fermin05", "LoSd1@bl0sRoJos!", null);
    Usuario usuarioContraseniaSeguraTres = new Usuario("fermin05", "UtnFRB4Medrano?", null);
    Usuario usuarioContraseniaSeguraCuatro = new Usuario("fermin05", "QuEG4444naSd3ReCiBIrme!!!!!", null);

    Arrays.stream(new Usuario[]{
        usuarioContraseniaSeguraUno,
        usuarioContraseniaSeguraDos,
        usuarioContraseniaSeguraTres,
        usuarioContraseniaSeguraCuatro
    }).forEach(usuario -> {
      Assertions.assertDoesNotThrow(() -> validador.esValidaContrasenia(usuario));
    });
  }

  @Test
  public void contraseniaInvalidaSinMayusculaTest() {
    verificarContraseniaInvalida("un4passs3gur4!", "La contraseña debe cumplir la expresión regular: .*[A-Z].* .No contiene mayuscula.");
  }

  @Test
  public void contraseniaInvalidaSinMinusculaTest() {
    verificarContraseniaInvalida("UN4PASSS3GUR4!", "La contraseña debe cumplir la expresión regular: .*[a-z].* . No contiene minuscula.");
  }

  @Test
  public void contraseniaInvalidaSinNumeroTest() {
    verificarContraseniaInvalida("UNPaSSSGUR!", "La contraseña debe cumplir la expresión regular: .*\\d.* . No contiene numero.");
  }

  @Test
  public void contraseniaInvalidaSinSignoDePuntuacionTest() {
    verificarContraseniaInvalida("uNP4SSSGUR", "La contraseña debe cumplir la expresión regular: .*[\\p{Punct}].* . No contiene signo de puntuacion.");
  }

  @Test
  public void contraseniaInvalidaMuyCortaTest() {
    verificarContraseniaInvalida("uNP4s!", "La contraseña debe tener al menos 8 caracteres.");
  }

  @Test
  public void contraseniaInvalidaMuyLargaTest() {
    verificarContraseniaInvalida("AgUanT3E!RojoAgUanT3E!RojoAgUanT3E!RojoAgUanT3E!RojoAgUanT3E!RojoAgUanT3E!RojoAgUanT3E!Rojo", "La contraseña debe tener como maximo 64 caracteres.");
  }

  @Test
  public void contraseniaInvalidaEstaEnListaPeoresTest() {
    verificarContraseniaInvalida("master", "La contraseña se encuentra en la lista de las 'Diez mil peores contraseñas'.");
  }

  @Test
  public void contraseniaInvalidaPorMasDeUnaRestriccionTest() {
    ValidadorContrasenias validador = new ValidadorContrasenias();
    Usuario usuarioConContrasenia = new Usuario("fermin05", "aguante!", null);
    String mensajeEsperadoUno = "La contraseña debe cumplir la expresión regular: .*[A-Z].* .No contiene mayuscula.";
    String mensajeEsperadoDos = "La contraseña debe cumplir la expresión regular: .*\\d.* .";

    ContraseniaInvalidaException e = Assertions.assertThrows(ContraseniaInvalidaException.class, () -> validador.esValidaContrasenia(usuarioConContrasenia));

    Assertions.assertTrue(e.getMessage().contains(mensajeEsperadoUno));
    Assertions.assertTrue(e.getMessage().contains(mensajeEsperadoDos));
  }
}
