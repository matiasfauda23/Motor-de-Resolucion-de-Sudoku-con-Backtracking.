package logica;

public class ValidadorSodoku {
	private Tablero tablero;
	private boolean estado; // define si el sodoku tiene o no solucion
	
	public ValidadorSodoku(Tablero tablero) {
		this.tablero = tablero;
		this.estado = estado;
	}
	
	public boolean esValidoEnFila(int fila, int numero) {
		for(int col = 0 ; col < 9 ; col++) {
			if(tablero.getGrilla()[fila][col].getValor() == numero) {
				return false;
			}
		}
		return true;
	}

	public boolean esValidoEnColumna(int columna,int numero) {
		for(int fila = 0 ;fila < 9 ; fila++) {
			if(tablero.getGrilla()[fila][columna].getValor() == numero) {
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
				if(tablero.getGrilla()[f][c].getValor() == numero) {
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
