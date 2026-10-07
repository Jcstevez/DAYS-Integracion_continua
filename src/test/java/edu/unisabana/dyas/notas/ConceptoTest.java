package edu.unisabana.dyas.notas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Pruebas de concepto(): cada línea de cobertura se acompaña de una aserción sobre el valor devuelto,
 * y se prueba a ambos lados de cada frontera (4.5, 4.0 y 3.0), que es donde suelen estar los errores.
 */
class ConceptoTest {

    private final CalculadoraNotas calculadora = new CalculadoraNotas();

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "5.0, Excelente",
            "4.5, Excelente",       // frontera inferior de Excelente
            "4.4, Sobresaliente",   // justo debajo de la frontera
            "4.0, Sobresaliente",   // frontera inferior de Sobresaliente
            "3.9, Aprobado",
            "3.0, Aprobado",        // frontera inferior de Aprobado
            "2.9, Reprobado",       // justo debajo de aprobar
            "0.0, Reprobado"
    })
    void clasificaLaDefinitivaEnSuConcepto(double definitiva, String esperado) {
        assertEquals(esperado, calculadora.concepto(definitiva));
    }

    @ParameterizedTest(name = "{0} fuera de rango")
    @CsvSource({"-0.1", "5.1"})
    void rechazaDefinitivasFueraDeRango(double definitiva) {
        assertThrows(IllegalArgumentException.class, () -> calculadora.concepto(definitiva));
    }

    @Test
    void elConceptoCoincideConLaReglaDeAprobacion() {
        // 4.0, 3.5 y 2.0 dan 3.1 (ver CalculadoraNotasTest): aprueba y su concepto es "Aprobado"
        double definitiva = calculadora.calcularDefinitiva(4.0, 3.5, 2.0);
        assertEquals("Aprobado", calculadora.concepto(definitiva));
        assertTrue(calculadora.aprueba(definitiva));
    }
}