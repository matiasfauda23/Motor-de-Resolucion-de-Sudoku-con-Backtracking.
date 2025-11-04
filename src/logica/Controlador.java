package logica;

public class Controlador {

    private Tablero tablero;

    public Controlador(Tablero t) {
        this.tablero = t;
    }
    
    public void setValor(int fila, int columna, int valor) {
    	tablero.setValor(fila, columna, valor);
    }
    
    public void borrarValor(int fila, int columna) {
    	tablero.borrarValor(fila, columna);
    }

    public void resolverSudoku() {
    	if(!tablero.esSoluble()) {
    		return;
    	}
    	if(tablero.estaResuelto()) {
    		return;
    	}
        tablero.resolver();
    }

    public void generarSudoku() {
    	if(!tablero.tieneLugaresVacios()) {
    		return;
    	}
        tablero.llenarAleatoriamente(17);
    }

    public void limpiarGrilla() {
        tablero.limpiar();
    }

	public int lugaresLibres() {
		return tablero.lugaresLibres();
	}
}
