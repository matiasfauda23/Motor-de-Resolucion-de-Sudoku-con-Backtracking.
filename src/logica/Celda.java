package logica;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Celda {
	private int valor ;
	private final int valorOriginal; // para reiniciar
	private boolean esPrefijada; // si es parte del sudoku inicial o no
	private HashSet<Integer> candidatos; 

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
	    this.candidatos = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
	}
	
	public void eliminarCandidato(int numero) {
		candidatos.remove(numero);		
	}
	
	public void agregarCandidato(int numero) {
		candidatos.add(numero);
	}
	
	public void reiniciar() {
	    if (!esPrefijada) {
	        valor = 0;
	    }
	    else {
	        valor = valorOriginal; //Si es prefijada, restaura el valor original
	    }
	    candidatos = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
	    }
	
	
    ///setters y getters
	public Set<Integer> getCandidatos() {
		return new HashSet<>(candidatos);
	}
	
	//Copio los candidatos para evitar aliasing
	public void setCandidatos(Set<Integer> candidatos) {
		this.candidatos = new HashSet<>(candidatos);
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public boolean getEsPrefijada() {
		return esPrefijada;
	}
	
	public void setEsPrefijada(boolean esPrefijada) {
		this.esPrefijada = esPrefijada;
	}
	
}
