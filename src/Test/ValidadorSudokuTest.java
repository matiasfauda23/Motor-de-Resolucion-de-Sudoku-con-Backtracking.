package Test;

import static org.junit.Assert.*;
import org.junit.Test;

import logica.Tablero;
import logica.Validador;

public class ValidadorSudokuTest {

	// Tablero de prueba (int[9][9])
	int[][] grillaTest = {
			{5, 3, 0,  0, 7, 0,  0, 0, 0},
			{6, 0, 0,  1, 9, 5,  0, 0, 0},
			{0, 9, 8,  0, 0, 0,  0, 6, 0},

			{8, 0, 0,  0, 6, 0,  0, 0, 3},
			{4, 0, 0,  8, 0, 3,  0, 0, 1},
			{7, 0, 0,  0, 2, 0,  0, 0, 6},

			{0, 6, 0,  0, 0, 0,  2, 8, 0},
			{0, 0, 0,  4, 1, 9,  0, 0, 5},
			{0, 0, 0,  0, 8, 0,  0, 7, 9}
	};

	// Tablero invalido (doble 5 en fila 0)
	int[][] grillaInvalida = {
			{5, 3, 0,  0, 7, 0,  0, 0, 5}, 
			{6, 0, 0,  1, 9, 5,  0, 0, 0},
			{0, 9, 8,  0, 0, 0,  0, 6, 0},
			{8, 0, 0,  0, 6, 0,  0, 0, 3},
			{4, 0, 0,  8, 0, 3,  0, 0, 1},
			{7, 0, 0,  0, 2, 0,  0, 0, 6},
			{0, 6, 0,  0, 0, 0,  2, 8, 0},
			{0, 0, 0,  4, 1, 9,  0, 0, 5},
			{0, 0, 0,  0, 8, 0,  0, 7, 9}
	};

	int[][] grillaCompletaValida = {
			{5, 3, 4,  6, 7, 8,  9, 1, 2},
			{6, 7, 2,  1, 9, 5,  3, 4, 8},
			{1, 9, 8,  3, 4, 2,  5, 6, 7},
			{8, 5, 9,  7, 6, 1,  4, 2, 3},
			{4, 2, 6,  8, 5, 3,  7, 9, 1},
			{7, 1, 3,  9, 2, 4,  8, 5, 6},
			{9, 6, 1,  5, 3, 7,  2, 8, 4},
			{2, 8, 7,  4, 1, 9,  6, 3, 5},
			{3, 4, 5,  2, 8, 6,  1, 7, 9}
	};
	int[][] grillaCompletaInvalida = {
			{9, 3, 4,  6, 7, 8,  9, 1, 2}, 
			{6, 7, 2,  1, 9, 5,  3, 4, 8},
			{1, 9, 8,  3, 4, 2,  5, 6, 7},
			{8, 5, 9,  7, 6, 1,  4, 2, 3},
			{4, 2, 6,  8, 5, 3,  7, 9, 1},
			{7, 1, 3,  9, 2, 4,  8, 5, 6},
			{9, 6, 1,  5, 3, 7,  2, 8, 4},
			{2, 8, 7,  4, 1, 9,  6, 3, 5},
			{3, 4, 5,  2, 8, 6,  1, 7, 9}
	};

	@Test
	public void testFallaSiNumeroRepetidoEnFila() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaTest);
		Validador validador = new Validador(tablero);
		// El 5 ya está en la fila 0
		assertFalse(validador.esMovimientoValido(0, 2, 5));
	}

	@Test
	public void testFallaSiNumeroRepetidoEnColumna() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaTest);
		Validador validador = new Validador(tablero);
		// El 6 ya está en la columna 0
		assertFalse(validador.esMovimientoValido(2, 0, 6));
	}

	@Test
	public void testFallaSiNumeroRepetidoEnCaja() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaTest);
		Validador validador = new Validador(tablero);
		// El 9 ya está en la caja que contiene (1,2)
		assertFalse(validador.esMovimientoValido(1, 2, 9));
	}

	@Test
	public void testPasaSiMovimientoEsValido() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaTest);
		Validador validador = new Validador(tablero);
		assertTrue(validador.esMovimientoValido(0, 2, 4));
	}

	@Test
	public void testEsTableroSolubleValido() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaTest);
		Validador validador = new Validador(tablero);

		assertTrue(validador.esTableroSoluble());
	}

	@Test
	public void testEsTableroSolubleInvalido() {
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(grillaInvalida);
		Validador validador = new Validador(tablero);

		assertFalse(validador.esTableroSoluble());
	}
	@Test
    public void testEsTableroCompletoValidoOK() {
        Tablero tablero = new Tablero();
        tablero.cargarDesdeMatriz(grillaCompletaValida);
        Validador validador = new Validador(tablero);
        
        assertTrue(validador.esTableroCompletoValido());
    }
    
    @Test
    public void testEsTableroCompletoFallaSiEstaIncompleto() {
        Tablero tablero = new Tablero();
        tablero.cargarDesdeMatriz(grillaTest);
        Validador validador = new Validador(tablero);
        
        assertFalse(validador.esTableroCompletoValido());
    }
    
    @Test
    public void testEsTableroCompletoFallaSiHayErrores() {
        Tablero tablero = new Tablero();
        tablero.cargarDesdeMatriz(grillaCompletaInvalida);
        Validador validador = new Validador(tablero);
        
        assertFalse(validador.esTableroCompletoValido());
    }


}

