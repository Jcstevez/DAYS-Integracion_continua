package edu.unisabana.dyas.notas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NotaNecesariaTest {

    private final CalculadoraNotas calculadora = new CalculadoraNotas();

    @ParameterizedTest(name = "{0}, {1} -> necesita {2}")
    @CsvSource({
            "3.0, 3.0, 3.0",
            "2.0, 2.0, 4.5",
            "3.5, 2.8, 2.8",
            "5.0, 5.0, 1.0"
    })
    void calculaLaNotaNecesariaEnElTercerCorte(double c1, double c2, double esperada) {
        assertEquals(esperada, calculadora.notaNecesariaTercerCorte(c1, c2));
    }

    @Test
    void avisaCuandoYaNoEsPosibleAprobar() {
        assertThrows(IllegalStateException.class, () -> calculadora.notaNecesariaTercerCorte(1.0, 1.0));
    }

    // ---- Casos de borde propios (más allá del enunciado) ----

    @ParameterizedTest(name = "{0}, {1} -> necesita exactamente {2}")
    @CsvSource({
            "1.7, 1.7, 5.0", // 4.95 sube a 5.0: es la última combinación posible
            "4.0, 4.0, 1.5", // 1.5 exacto, sin redondeo
            "0.0, 5.0, 3.8", // 3.75 sube a 3.8
            "5.0, 0.0, 3.8", // el orden de los cortes no cambia el resultado
            "4.5, 4.5, 0.8"  // 0.75 sube a 0.8
    })
    void redondeaHaciaArribaAUnaDecima(double c1, double c2, double esperada) {
        assertEquals(esperada, calculadora.notaNecesariaTercerCorte(c1, c2));
    }

    @Test
    void lanzaExcepcionEnElPrimerValorImposible() {
        // 1.6 y 1.7 piden 5.025: pasa de 5.0, así que ya no es posible aprobar
        assertThrows(IllegalStateException.class, () -> calculadora.notaNecesariaTercerCorte(1.6, 1.7));
    }

    @Test
    void sinNingunaNotaNoSePuedeAprobar() {
        assertThrows(IllegalStateException.class, () -> calculadora.notaNecesariaTercerCorte(0.0, 0.0));
    }

    @ParameterizedTest(name = "{0}, {1} fuera de rango")
    @CsvSource({"-0.1, 3.0", "3.0, 5.1", "5.5, 3.0", "3.0, -2.0"})
    void validaLasNotasDeEntradaIgualQueLaDefinitiva(double c1, double c2) {
        assertThrows(IllegalArgumentException.class, () -> calculadora.notaNecesariaTercerCorte(c1, c2));
    }
}