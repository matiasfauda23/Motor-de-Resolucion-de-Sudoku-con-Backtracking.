package logica;

public class Celda {
	public int valor ;
	public boolean esPrefijada;

	public Celda(int valor, boolean esPrefijada) {
	    if(valor < 0 || valor > 9) { // Permitimos 0
	        throw new IllegalArgumentException("El valor de la celda debe estar entre 0 y 9");
	    }
	    //Podemos crear celda vacia (valor 0) o con valor entre 1 y 9
	    if(valor == 0 && esPrefijada) { 
	        throw new IllegalArgumentException("Una celda vacia (valor 0) no puede ser prefijada");
	    }
	    
	    this.valor = valor;
	    this.esPrefijada = esPrefijada;
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
