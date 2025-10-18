package logica;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Celda {
	private int valor ;
	private boolean esPrefijada; // si es parte del sudoku inicial o no
	private HashSet<Integer> candidatos; 

	public Celda(int valor, boolean esPrefijada) {
		if(valor < 1 || valor > 9) {
			throw new IllegalArgumentException("El valor de la celda debe estar entre 1 y 9");
		}
		this.valor = valor;
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
	    // Restaurar candidatos a {1, 2, 3, 4, 5, 6, 7, 8, 9}
	    candidatos = new HashSet<>();
	    for (int i = 1; i <= 9; i++) {
	        candidatos.add(i);
	    }
	}
	
    ///setters y getters
	public Set<Integer> getCandidatos() {
		return new HashSet<>(candidatos);
	}
	
	public void setCandidatos(Set<Integer> candidatos) {
		this.candidatos = (HashSet<Integer>) candidatos;
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
