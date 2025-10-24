package logica;
import interfaz.PantallaPrincipal;

public class Controlador{
	//Atributos
	private Tablero _modelo;
	private PantallaPrincipal _vista;

	//Constructor
	public Controlador(PantallaPrincipal vista) {
		this._vista = vista;
		conectarBotones();
	}

	private void conectarBotones() {
		_vista.getBotonResolver().addActionListener(e -> resolverSudoku());
		_vista.getBotonLimpiar().addActionListener(e -> limpiarGrilla());
		_vista.getBotonGenerar().addActionListener(e -> generarSudoku());
	}

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
        }
    }
	
	private void generarSudoku() {
		
        GeneradorSudoku generador = new GeneradorSudoku();
        
        int celdasPrefijadas = 30; 
        int[][] puzzle = generador.generar(celdasPrefijadas);
        
        _vista.limpiarTablero();
        _vista.setNuevoPuzzle(puzzle);
    }

	private void limpiarGrilla() {
        _vista.limpiarTablero();
    }
}
