package logica;

public class Caja {
    private Celda[][] celdas;

    public Caja() {
        celdas = new Celda[3][3];
    }
    
    // Nuevo método: buscar la próxima celda vacía dentro de esta caja
    public int[] encontrarProximaCeldaVacia(int cajaFila, int cajaCol) {
        for (int filaEnCaja = 0; filaEnCaja < 3; filaEnCaja++) {
            for (int colEnCaja = 0; colEnCaja < 3; colEnCaja++) {
                Celda celda = getCelda(filaEnCaja, colEnCaja);
                if (celda != null && celda.getValor() == 0) {
                    // Convertir a coordenadas globales
                    int filaGlobal = cajaFila * 3 + filaEnCaja;
                    int colGlobal = cajaCol * 3 + colEnCaja;
                    return new int[]{filaGlobal, colGlobal, filaEnCaja, colEnCaja};
                }
            }
        }
        return null;
    }
    
    // Método para verificar si la caja está completa (sin celdas vacías)
    public boolean estaCompleta() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                if (getCelda(fila, col).getValor() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Método para obtener valores únicos de la caja (para validación)
    public boolean contieneValor(int valor) {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                if (getCelda(fila, col).getValor() == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setCelda(int fila, int col, Celda celda) {
        if (fila < 0 || fila >= 3 || col < 0 || col >= 3)
            throw new IllegalArgumentException("Índices de celda inválidos en caja");
        celdas[fila][col] = celda;
    }

    public Celda getCelda(int fila, int col) {
        return celdas[fila][col];
    }

    public Celda[][] getCeldas() {
        return celdas;
    }
}
