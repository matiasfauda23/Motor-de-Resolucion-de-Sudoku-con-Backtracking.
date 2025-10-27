package logica;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private Tablero tablero;
    private Validador validador;
    private List <int[][]> soluciones;
	
    
    public Solver(Tablero tablero) {
        this.tablero = tablero;
        this.validador = new Validador(tablero);
        this.soluciones = new ArrayList<>();
        
    }
       
    public boolean resolver() {
        // 1) Buscamos/elegimos un casillero vacío
        int[] posicionVacia = encontrarProximaCeldaVacia();
        
        // 2) Si no hay celdas vacías, el tablero está resuelto
        if (posicionVacia == null) {
            return true;
        }
        
        // Obtenemos fila y columna de la celda vacía
        int fila = posicionVacia[0];
        int col = posicionVacia[1];
        
        // 3) Intentamos números del 1 al 9
        for (int num = 1; num <= 9; num++) {
            if (validador.esMovimientoValido(fila, col, num)) {
                // 4) Si el número es válido, lo ponemos en la celda
                tablero.getGrilla()[fila][col].setValor(num);
                
                // 5) Llamada recursiva para que continúe resolviendo
                if (resolver()) {
                    return true; // Si se pudo resolver, retornamos true
                }
                
                // 6) Si no se pudo resolver, hacemos backtracking
                tablero.getGrilla()[fila][col].setValor(0);
            }
        }
        
        // 7) Si ningún número del 1 al 9 funcionó, retornamos false
        return false;
    }
    
  
     // Encuentra la próxima celda vacía en el tablero
     // return array con [fila, col] o null si no hay celdas vacías
     
    private int[] encontrarProximaCeldaVacia() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero.getGrilla()[fila][col].getValor() == 0) {
                    return new int[]{fila, col};
                }
            }
        }
        return null; // No hay celdas vacías
    }
}