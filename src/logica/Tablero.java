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
	
	//Para el metodo backtracking necesito 2 metodos. Un metodo 
	//que encuentre la proxima celda vacia y el metodo resolver() recursivo
	
	public int[] encontrarProximaCeldaVacia() {
		for(int fila = 0 ; fila < 9 ; fila++) {
			for(int col = 0 ; col < 9 ; col++) {
				if(grilla[fila][col].getValor() == 0) {
					//Retorno la posicion de la celda vacia
					return new int[]{fila, col};
				}
			}
		}
		return null; //No hay celdas vacias
	}

	//Metodo resolver con backtracking
	public boolean resolver() {
		//1)Buscamos/elegimos un casillero vacio
		int[] posicionVacia = encontrarProximaCeldaVacia();
		//2)Si no hay celdas vacias, el tablero esta resuelto
		if(posicionVacia == null) {
			return true;
		}
		//Obtenemos fila y columna de la celda vacia
		int fila = posicionVacia[0];
		int col = posicionVacia[1];
		
		//3)Intentamos numeros del 1 al 9
		for(int num = 1 ; num <= 9 ; num++) {
			if(esMovimientoValido(fila, col, num)) {
				//4)Si el numero es valido, lo ponemos en la celda
				grilla[fila][col].setValor(num);
				//5)Llamada recursiva para que continue resolviendo
				if(resolver()) {
					return true; //Si se pudo resolver, retornamos true
				}
				//6)Si no se pudo resolver, hacemos backtracking
				grilla[fila][col].setValor(0);
			}
		}
		//7)Si ningun numero del 1 al 9 funciono, retornamos false para backtracking
		return false;
		
	}
	
	public int getValor(int fila, int col) {
		if (fila < 0 || fila > 8 || col < 0 || col > 8) {
			throw new IllegalArgumentException("Fila y columna deben estar entre 0 y 8.");
		}
		return grilla[fila][col].getValor();
	}
	
	
	public int[][] getGrillaSolucion() {
	    int[][] solucion = new int[9][9];
	    for (int f = 0; f < 9; f++) {
	        for (int c = 0; c < 9; c++) {
	            solucion[f][c] = this.grilla[f][c].getValor();
	        }
	    }
	    return solucion;
	}
	
	public Celda[][] getGrilla() {
		return grilla;
	}
	
}
