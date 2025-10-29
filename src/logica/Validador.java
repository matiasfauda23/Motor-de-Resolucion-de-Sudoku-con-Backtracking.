package logica;

public class Validador {
    private Tablero _tablero;
    
    public Validador(Tablero tablero) {
        this._tablero = tablero;
    }
    
    public boolean esMovimientoValido(int fila, int col, int numero) {	
        return esValidoEnFila(fila, numero) &&
               esValidoEnColumna(col, numero) &&
               esValidoEnCaja(fila, col, numero);
    }
    
    private boolean esValidoEnFila(int fila, int numero) {
        for (int col = 0; col < 9; col++) {
            if (_tablero.getValor(fila, col) == numero) {
                return false;
            }
        }
        return true;
    }

    private boolean esValidoEnColumna(int columna, int numero) {
        for (int fila = 0; fila < 9; fila++) {
            if (_tablero.getValor(fila, columna) == numero) {
                return false;
            }
        }
        return true;
    }
    
    private boolean esValidoEnCaja(int fila, int col, int numero) {			
        int filaInicio = (fila / 3) * 3;
        int colInicio = (col / 3) * 3;

        for (int f = filaInicio; f < filaInicio + 3; f++) {
            for (int c = colInicio; c < colInicio + 3; c++) {
                if (_tablero.getValor(f, c) == numero) {
                    return false;
                }
            }
        }
        return true;
    }
    

}

