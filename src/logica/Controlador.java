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
        if (!tablero.esSoluble()) {
            return;
        }
        if(tablero.estaResuelto()) {
        	return;
        }      
    	long inicio=System.currentTimeMillis();
        
        tablero.encontrarTodasLasSoluciones();
        
        long fin=System.currentTimeMillis();
        double tiempo=(fin-inicio)/1000.0;        
        tablero.setTiempoResolucion(tiempo);             
        
        int cantidad = tablero.getCantidadSoluciones(); 
        
        if (cantidad == 0) {
            tablero.noEsSolubleObservadores();
        } else {
            tablero.cargarSolucion(0); 
            tablero.notificarConteoSoluciones(); 
        }
    }

//    public List<Tablero> generarMultiplesSudokus(int cantidad, int numerosAColocar) {
//		List<Tablero> sudokusGenerados = new List<Tablero>();
//		for (int i = 0; i < cantidad; i++) {
//			Tablero Tablero = new Tablero();
//			Tablero.llenarAleatoriamente(numerosAColocar);
//			Tablero.encontrarTodasLasSoluciones();
//		}
//		
//		return sudokusGenerados;
//	} 
 
    public void generarSudoku(int cantidad) {
        if (!tablero.tieneLugaresVacios()) {
            return;
        }
        tablero.llenarAleatoriamente(cantidad);
    }

    public void limpiarGrilla() {
        tablero.limpiar();
    }
    
    public void mostrarSiguienteSolucion() {
        int actual = tablero.getIndiceSolucionActual();
        if (actual < tablero.getCantidadSoluciones() - 1) {
            tablero.cargarSolucion(actual + 1);
            tablero.notificarConteoSoluciones();
        }
    }

    public void mostrarAnteriorSolucion() {
        int actual = tablero.getIndiceSolucionActual();
        if (actual > 0) {
            tablero.cargarSolucion(actual - 1);
            tablero.notificarConteoSoluciones();
        }
    }

    public int getIndiceSolucionActual() {
        return tablero.getIndiceSolucionActual();
    }

	public int lugaresLibres() {
		return tablero.lugaresLibres();
	}
		
}
