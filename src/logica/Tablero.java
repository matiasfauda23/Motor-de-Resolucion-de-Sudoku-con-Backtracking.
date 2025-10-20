package logica;

public class Tablero {
	
	private Celda[][] grilla;
	
	// Constructor que recibe matriz
	public Tablero(int [][] numeros) {
		this.grilla = new Celda [9][9];
		inicializarGrilla(numeros);
		
	}
	
	private void inicializarGrilla(int [][] numeros) {
		for(int fila = 0 ; fila < 9 ; fila++) {
			for(int col = 0 ; col < 9 ; col ++) {
				int valor = numeros[fila][col];
				boolean esPrefijada = (valor != 0);
				this.grilla[fila][col] = new Celda (valor, esPrefijada);
			}
		}
	}
	private boolean esValidoEnFila(int fila, int numero) {
		for(int col = 0 ; col < 9 ; col++) {
			if(grilla[fila][col].getValor() == numero) {
				return false;
			}
		}
		return true;
	}
	private boolean esValidoEnColumna(int columna, int numero) {
		for(int fila = 0 ; fila < 9 ; fila++) {
			if(grilla[fila][columna].getValor() == numero) {
				return false;
			}
		}
		return true;
	}
	
	private boolean esValidoEnCaja(int fila, int col, int numero) {
		//Encuentro la esquina superior izquierda de la caja
		int filaInicio = (fila / 3) * 3;
		int colInicio = (col / 3) * 3;
		//Recorro solo la caja
		for(int f = filaInicio ; f < filaInicio + 3 ; f++) {
			for(int c = colInicio ; c < colInicio + 3 ; c++) {
				if(grilla[f][c].getValor() == numero) {
					return false;
				}
			}
		}
		return true;
	}
	
	//Verifico si un movimiento es valido
	public boolean esMovimientoValido(int fila, int col, int numero) {
		return esValidoEnFila(fila, numero) &&
		       esValidoEnColumna(col, numero) &&
		       esValidoEnCaja(fila, col, numero);
	}

}
