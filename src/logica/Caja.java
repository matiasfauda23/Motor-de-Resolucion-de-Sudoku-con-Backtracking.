package logica;

public class Caja {
    private Celda[][] celdas;

    public Caja() {
        celdas = new Celda[3][3];
    }
            
    public void setCelda(int fila, int col, Celda celda) {
        if (fila < 0 || fila >= 3 || col < 0 || col >= 3)
            throw new IllegalArgumentException("Índices de celda inválidos en caja");
        celdas[fila][col] = celda;
    }

    public Celda getCelda(int fila, int col) {
        return celdas[fila][col];
    }

}
