package logica;

public class Validador {
	private Tablero tablero;
	
	public Validador(Tablero tablero) {
		this.tablero = tablero;
		
	}
	
	public boolean esValidoEnFila(int fila, int numero) {
		for(int col = 0 ; col < 9 ; col++) {
			if(tablero.getValor(fila, col) == numero) {
				return false;
			}
		}
		return true;
	}

	public boolean esValidoEnColumna(int columna,int numero) {
		for(int fila = 0 ;fila < 9 ; fila++) {
			if(tablero.getValor(fila, columna) == numero) {
				return false;
			}
		}
	return true;
	}
	
	public boolean esValidoEnCaja(int fila,int col,int numero) {			
		//Encuentro la esquina superior izquierda de la caja
		int filaInicio = (fila / 3) * 3;
		int colInicio = (col / 3) * 3;
		//Recorro solo la caja
		for(int f = filaInicio ; f < filaInicio + 3 ; f++) {
			for(int c = colInicio ; c < colInicio + 3 ; c++) {
				if(tablero.getValor(filaInicio, colInicio) == numero) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean esMovimientoValido(int fila,int col,int numero) {	
		return esValidoEnFila(fila, numero) &&
		       esValidoEnColumna(col, numero) &&
		       esValidoEnCaja(fila, col, numero);
	}
}
