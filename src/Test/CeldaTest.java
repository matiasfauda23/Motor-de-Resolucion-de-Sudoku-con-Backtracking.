package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.Celda;

public class CeldaTest {
	
	//Tests de celdas correctas
	@Test
	public void testCrearCeldaPrefijada() {
		Celda celda = new Celda(5, true);
		assertEquals(5, celda.getValor());
		assertTrue(celda.getEsPrefijada());
	}
	@Test
	public void testCrearCeldaVacia() {
		Celda celda = new Celda(0, false);
		assertEquals(0, celda.getValor());
		assertFalse(celda.getEsPrefijada());
	}
	
	//Tests de fallas en constructor
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFallaSiValorEsInvalido() {
	    new Celda(10, false);
	
	}
	@Test(expected = IllegalArgumentException.class)
	public void TestConstructorFallaSiCeldaVaciaEsPrefijada() {
		new Celda(0, true);;
	}

}
