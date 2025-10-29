package logica;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Celda {
	private int valor ;
	private final int valorOriginal; // para reiniciar
	private boolean esPrefijada; // si es parte del sudoku inicial o no

	public Celda(int valor, boolean esPrefijada) {
	    if(valor < 0 || valor > 9) { // Permitimos 0
	        throw new IllegalArgumentException("El valor de la celda debe estar entre 0 y 9");
	    }
	    //Podemos crear celda vacia (valor 0) o con valor entre 1 y 9
	    if(valor == 0 && esPrefijada) { 
	        throw new IllegalArgumentException("Una celda vacia (valor 0) no puede ser prefijada");
	    }
	    
	    this.valor = valor;
	    this.valorOriginal = valor;
	    this.esPrefijada = esPrefijada;
	}
	
	
	public void reiniciar() {
	    if (!esPrefijada) {
	        valor = 0;
	    }
	    else {
	        valor = valorOriginal; //Si es prefijada, restaura el valor original
	    }
	}
	
    ///setters y getters
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public boolean getEsPrefijada() {
		return esPrefijada;
	}
	
	public void setEsPrefijada(boolean bol) {
		this.esPrefijada=bol;
	}
}
