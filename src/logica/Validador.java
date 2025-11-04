package logica;

public class Validador {
    private final Tablero tablero;

    public Validador(Tablero tablero) {
        this.tablero = tablero;
    }

    // ---------------- VALIDACIÓN GENERAL ----------------
    public boolean esMovimientoValido(int fila, int col, int numero) {
        return esValidoEnFila(fila, numero)
            && esValidoEnColumna(col, numero)
            && esValidoEnCaja(fila, col, numero);
    }

    // ---------------- VALIDACIÓN POR FILA ----------------
    private boolean esValidoEnFila(int fila, int numero) {
        for (int col = 0; col < 9; col++) {
            if (tablero.getCelda(fila, col).getValor() == numero) {
                return false;
            }
        }
        return true;
    }

    // ---------------- VALIDACIÓN POR COLUMNA ----------------
    private boolean esValidoEnColumna(int columna, int numero) {
        for (int fila = 0; fila < 9; fila++) {
            if (tablero.getCelda(fila, columna).getValor() == numero) {
                return false;
            }
        }
        return true;
    }

    // ---------------- VALIDACIÓN POR CAJA 3x3 ----------------
    private boolean esValidoEnCaja(int fila, int col, int numero) {
        int filaInicio = (fila / 3) * 3;
        int colInicio = (col / 3) * 3;

        for (int f = filaInicio; f < filaInicio + 3; f++) {
            for (int c = colInicio; c < colInicio + 3; c++) {
                if (tablero.getCelda(f, c).getValor() == numero) {
                    return false;
                }
            }
        }
        return true;
    }

    // ---------------- COMPROBACIÓN DE SOLUBILIDAD ----------------
    public boolean esTableroSoluble() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = tablero.getCelda(fila, col);
                int valor = celda.getValor();

                // Solo revisamos las prefijadas con valor distinto de 0
                if (valor != 0 && celda.getEsPrefijada()) {
                    // Temporariamente vaciamos la celda para no compararse a sí misma
                    celda.setValor(0);

                    if (!esMovimientoValido(fila, col, valor)) {
                        celda.setValor(valor); // restauramos
                        return false;
                    }

                    celda.setValor(valor); // restauramos
                }
            }
        }
        return true;
    }
    
 // ---------------- VALIDACIÓN DE TABLERO COMPLETO ----------------
    public boolean esTableroCompletoValido() {
        // Primero verificar que no haya celdas vacías
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero.getCelda(fila, col).getValor() == 0) {
                    return false;
                }
            }
        }
        
        // Verificar que todas las celdas cumplan las reglas del Sudoku
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = tablero.getCelda(fila, col);
                int valor = celda.getValor();
                
                // Temporariamente vaciamos la celda para validar sin autocontarse
                celda.setValor(0);
                
                boolean esValido = esMovimientoValido(fila, col, valor);
                
                // Restauramos el valor
                celda.setValor(valor);
                
                if (!esValido) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
}
