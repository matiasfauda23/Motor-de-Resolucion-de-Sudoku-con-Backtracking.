package logica;

import static org.junit.Assert.*;

import org.junit.Test;

public class CeldaTest {

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
	@Test
	public void testConstructorFallaSinValorEsInvalido() {
		//asserrThrows toma dos parametros: la excepcion esperada y el codigo que tiene que fallar
		assertThrows(IllegalArgumentException.class, () -> {new Celda (10, false);});
		assertThrows(IllegalArgumentException.class, () -> {new Celda (-1, false);});
	}

}
