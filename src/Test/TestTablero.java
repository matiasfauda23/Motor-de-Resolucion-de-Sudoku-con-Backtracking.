package Test;

import static org.junit.Assert.assertEquals;
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
	
	//Vamos a probar que el algoritmo de backtracking resuelva tablero con solucion
	@Test
	public void testResolverTableroValido() {
		int [][] problema = {
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
		int[][] solucion = {
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
		Tablero tablero = new Tablero(problema);
		boolean tieneSolucion = tablero.resolver();
		assertTrue(tieneSolucion);
		//Verifico que la solucion sea correcta
		for (int f = 0; f < 9; f++) {
	        for (int c = 0; c < 9; c++) { 
	            String mensajeError = "Error en la celda [" + f + "][" + c + "]";
	            assertEquals(mensajeError, solucion[f][c], tablero.getValor(f, c));
	        }
	    }
		}
	
	@Test
	public void testResolverFallaEnSudokuImposible() {
	    int[][] problemaImposible = {
	        {5, 3, 0,  0, 7, 0,  0, 0, 5}, // doble 5 en la fila
	        {6, 0, 0,  1, 9, 5,  0, 0, 0},
	        {0, 9, 8,  0, 0, 0,  0, 6, 0},
	        {8, 0, 0,  0, 6, 0,  0, 0, 3},
	        {4, 0, 0,  8, 0, 3,  0, 0, 1},
	        {7, 0, 0,  0, 2, 0,  0, 0, 6},
	        {0, 6, 0,  0, 0, 0,  2, 8, 0},
	        {0, 0, 0,  4, 1, 9,  0, 0, 5},
	        {0, 0, 0,  0, 8, 0,  0, 7, 9}
	    };
	    
	    Tablero tablero = new Tablero(problemaImposible);
	    boolean tieneSolucion = tablero.resolver();
	    assertFalse(tieneSolucion);
	}
	}
