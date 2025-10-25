package logica;
import java.util.Random;

public class GeneradorSudoku {
	private Random _random;
	 
	// Constructor
	public GeneradorSudoku() {
		this._random = new Random();
	}
	
public int[][] generar(int celdasPrefijadas) {
        
        celdasPrefijadas = ajustarCantidadDePistas(celdasPrefijadas);
        // Obtenemos una solucion completa
        int[][] solucionCompleta = crearSolucionCompleta();

        // Creo puzzle vacio
        int[][] puzzle = new int[9][9]; // Todo en 0
        
        // Ponemos 1 pista garantizada por caja
        garantizarPistasPorCaja(puzzle, solucionCompleta);
        
        // Ponemos el resto de las pistas
        int pistasRestantes = celdasPrefijadas - 9;
        rellenarPistasRestantes(puzzle, solucionCompleta, pistasRestantes);
        return puzzle;
    }

private int ajustarCantidadDePistas(int celdasPrefijadas) {
	// Validacion de entradas
	if (celdasPrefijadas < 9) {
	    celdasPrefijadas = 9;
	}
	if (celdasPrefijadas > 80) {
	    celdasPrefijadas = 80;
	}
	return celdasPrefijadas;
}

    
    private int[][] crearSolucionCompleta() {
        Tablero tablero = new Tablero(new int[9][9]);
        tablero.resolver();
        return tablero.getGrillaSolucion();
    }
    
    private void garantizarPistasPorCaja(int[][] puzzle, int[][] solucionCompleta) {
        
        for (int i = 0; i < 9; i++) {
            // Calculamos la esquina
            int filaInicioCaja = (i / 3) * 3;
            int colInicioCaja = (i % 3) * 3;

            // Elegimos un offset aleatorio (0, 1, o 2) dentro de la caja
            int filaOffset = _random.nextInt(3);
            int colOffset = _random.nextInt(3);

            // La celda aleatoria final
            int fila = filaInicioCaja + filaOffset;
            int col = colInicioCaja + colOffset;

            // Copiamos el valor
            puzzle[fila][col] = solucionCompleta[fila][col];
        }
    }
    
    private void rellenarPistasRestantes(int[][] puzzle, int[][] solucionCompleta, int pistasRestantes) {
        
        while (pistasRestantes > 0) {
            // Elegimos una celda al azar en toda la grilla
            int fila = _random.nextInt(9);
            int col = _random.nextInt(9);

            // Si esa celda toavia esta vacia
            if (puzzle[fila][col] == 0) {
                // La llenamos
                puzzle[fila][col] = solucionCompleta[fila][col];
                pistasRestantes--;
            }
            // Si no estaba vacia, probamos otra celda
        }
    }
	
}
