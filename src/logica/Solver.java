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
        	
        	guardarSolucion();
        	return false; // cambiar a false si se quiere encontrar todas las soluciones 
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
                resolver();
//                if (resolver()) {                	
//                    return true; // Si se pudo resolver, retornamos true
//                
//                }               
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
    
    private void guardarSolucion() {
		int[][] solucionActual = new int[9][9];
		for (int fila = 0; fila < 9; fila++) {
			for (int col = 0; col < 9; col++) {
				solucionActual[fila][col] = tablero.getGrilla()[fila][col].getValor();
			}
		}
		soluciones.add(solucionActual);
	}
    
    public List<int[][]> getSoluciones() {
		return soluciones;
	}
    
/////////////////MAIN///////////////////////////////////////
        
        public static void main(String[] args) {
            
            // Sudoku de ejemplo 
            int[][] sudokuEj = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
            };
                             
            System.out.println("=== SUDOKU ejemplo ===");
            imprimirTablero(sudokuEj); 
            
            // Crear tablero y solver
            Tablero tablero = new Tablero(sudokuEj);
            Solver solver = new Solver(tablero);
                        
            // Resolver
            System.out.println("\n=== RESOLVIENDO... ===\n");     
            if (solver.resolver()) {
                System.out.println(" ¡Sudoku resuelto exitosamente!\n");
                System.out.println("=== SOLUCIÓN ===");
                
            } else {
                System.out.println(" No se encontró solución para este sudoku");
            }
            int[][] solucion = tablero.getGrillaSolucion();
            imprimirTablero(solucion);
            
            System.out.println("Número de soluciones encontradas: " + solver.getSoluciones().size());
            imprimirTablero(solver.getSoluciones().get(3));
            
            
            
        }
               
//         Método auxiliar para imprimir el tablero 
         
        private static void imprimirTablero(int[][] tablero) {
            for (int fila = 0; fila < 9; fila++) {
                if (fila % 3 == 0 && fila != 0) {
                    System.out.println("------+-------+------");
                }
                for (int col = 0; col < 9; col++) {
                    if (col % 3 == 0 && col != 0) {
                        System.out.print("| ");
                    }
                    int valor = tablero[fila][col];
                    System.out.print((valor == 0 ? "." : valor) + " ");
                }
                System.out.println();
            }
        }
    }    			
    