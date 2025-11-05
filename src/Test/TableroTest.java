package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logica.Tablero;

public class TableroTest {
	
	//caso borde: tablero vacio
	@Test
	public void testResolverTableroVacio() {
		int [][] problema = {
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},

		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},

		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0,  0, 0, 0,  0, 0, 0},
		        {0, 0, 0, 0, 0, 0,  0, 0, 0}
		    };
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(problema);
		boolean tieneSolucion = tablero.resolver();
		assertTrue(tieneSolucion);
	}
	
	@Test
	public void testResolverTableroAleatorio() {
		Tablero tablero = new Tablero();
		tablero.llenarAleatoriamente(20); 
		boolean tieneSolucion = tablero.resolver();
		assertTrue(tieneSolucion);
	}
	
	//algoritmo de backtracking happy path
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
		Tablero tablero = new Tablero();
		tablero.cargarDesdeMatriz(problema);
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
	    
	    Tablero tablero = new Tablero();
	    tablero.cargarDesdeMatriz(problemaImposible);
	    boolean tieneSolucion = tablero.resolver();
	    System.out.println(tieneSolucion);
	    assertFalse(tieneSolucion);
	}
	
	//Grilla de prueba para test
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

		@Test
		public void testLimpiarBorraInputDeUsuarioPeroNoPrefijadas() {
		    Tablero tablero = new Tablero();
		    tablero.cargarDesdeMatriz(grillaTest); 
		    tablero.setValor(0, 2, 9);
		    tablero.limpiar(); // Debería borrar el 9, pero no el 5

		    assertEquals("El valor prefijado (0,0) no debe borrarse", 
		                 5, tablero.getValor(0, 0));
		    assertEquals("El valor del usuario (0,2) debe borrarse", 
		                 0, tablero.getValor(0, 2));
		}
	
	}
