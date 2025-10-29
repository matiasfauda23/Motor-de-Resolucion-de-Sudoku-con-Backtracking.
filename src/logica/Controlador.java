package logica;

public class Controlador {

    private Tablero tablero;

    public Controlador(Tablero t) {
        this.tablero = t;
    }

<<<<<<< HEAD
    public void resolverSudoku() {
        boolean exito = tablero.resolver();
        if (!exito) {
            System.out.println("No se pudo resolver el Sudoku.");
=======
	private void resolverSudoku() {
		try {
            int[][] datos = _vista.getDatosDeGrilla();
            _modelo = new Tablero(datos);
           
            if (_modelo.resolver()) {
                int[][] solucion = _modelo.getGrillaSolucion();
                _vista.setDatosEnGrilla(solucion);
                
            } else {
                _vista.mostrarError("El Sudoku ingresado no tiene solucion.");
            }
             
        } 
		catch (Exception e) {
            _vista.mostrarError("Error en los datos ingresados: " + e.getMessage());
>>>>>>> c90014fc58c778a06a3c1850f5c712420780caba
        }
        tablero.notificarObservadores();

    }

    public void generarSudoku() {
        tablero.llenarAleatoriamente();
    }

    public void limpiarGrilla() {
        tablero.limpiar();
    }
}
