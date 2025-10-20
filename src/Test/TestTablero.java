package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logica.Tablero;

public class TestTablero {

	// Tablero de prueba (un int[9][9])
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
	
	Tablero tablero = new Tablero(grillaTest);
	
	@Test
	public void testFallaSiNumeroRepetidoEnFila() {
		//El 5 ya esta en la fila 0
		assertFalse(tablero.esMovimientoValido(0, 2, 5));
	}
	
	@Test
	public void testFallaSiNumeroRepetidoEnColumna() {
		//El 6 ya esta en la columna 0
		assertFalse(tablero.esMovimientoValido(2, 0, 6));
	}
	
	@Test
	public void testFallaSiNumeroRepetidoEnCaja() {
		//El 9 ya esta en la caja que contiene (1,2)
		assertFalse(tablero.esMovimientoValido(1, 2, 9));
	}
	@Test
	public void testPasaSiMovimientoEsValido() {
		//El 4 no esta en la fila 0, columna 2 ni en la caja que contiene (0,2)
		assertTrue(tablero.esMovimientoValido(0, 2, 4));
	}
}
