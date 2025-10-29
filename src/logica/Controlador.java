package logica;

public class Controlador {

    private Tablero tablero;

    public Controlador(Tablero t) {
        this.tablero = t;
    }

    public void resolverSudoku() {
        boolean exito = tablero.resolver();
        if (!exito) {
            System.out.println("No se pudo resolver el Sudoku.");
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
