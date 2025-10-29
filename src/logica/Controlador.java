package logica;

public class Controlador {

    private Tablero tablero;

    public Controlador(Tablero t) {
        this.tablero = t;
    }
    
    public void setValor(int fila, int columna, int valor) {
    	tablero.setValor(fila, columna, valor);
    }

    public void resolverSudoku() {
    	if(!tablero.esSoluble()) {
    		return;
    	}
        tablero.resolver();
    }

    public void generarSudoku() {
        tablero.llenarAleatoriamente(17);
    }

    public void limpiarGrilla() {
        tablero.limpiar();
    }
}
