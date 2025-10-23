package Test;

import org.junit.Test;

import logica.Tablero;
import logica.ValidadorSodoku;

public class ValidadorSodokuTest {
	
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
	ValidadorSodoku validador = new ValidadorSodoku(tablero);
	
	@Test
	public void esValidoEnFilaTest() {
		assert !validador.esValidoEnFila(0, 5);
	}
	
	@Test
	public void esValidoEnColumnaTest() {
		assert !validador.esValidoEnColumna(0, 6);
	}
	
	@Test
	public void esValidoEnCajaTest() {
		assert !validador.esValidoEnCaja(1, 2, 9);
	}
	
	@Test
	public void esMovimientoValidoTest() {
	    //El 4 no esta en la fila 0, columna 2 ni en la caja que contiene (0,2)
		assert validador.esMovimientoValido(0, 2, 4);
	}
	
}
