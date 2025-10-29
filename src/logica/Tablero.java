package logica;

import java.util.ArrayList;

public class Tablero {

    private Celda[][] grilla;
    private Validador validador = new Validador(this);
    private ArrayList<Observador> observadores = new ArrayList<>();

    // Constructor que recibe matriz inicial
    public Tablero(int[][] numeros) {
        this.grilla = new Celda[9][9];
        inicializarGrilla(numeros);
    }

    // Constructor vacío para crear un tablero en blanco
    public Tablero() {
        this(new int[9][9]);
    }

    // ---------------- OBSERVER ----------------
    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar();
        }
    }

    // ---------------- INICIALIZACIÓN ----------------
    private void inicializarGrilla(int[][] numeros) {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = numeros[fila][col];
                boolean esPrefijada = (valor != 0);
                this.grilla[fila][col] = new Celda(valor, esPrefijada);
            }
        }
    }

    // ---------------- LÓGICA DE SUDOKU ----------------
    public int[] encontrarProximaCeldaVacia() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (grilla[fila][col].getValor() == 0) {
                    return new int[]{fila, col};
                }
            }
        }
        return null;
    }

    public boolean resolver() {
        int[] pos = encontrarProximaCeldaVacia();
        if (pos == null) return true;

        int fila = pos[0];
        int col = pos[1];

        for (int num = 1; num <= 9; num++) {
            if (validador.esMovimientoValido(fila, col, num)) {
                grilla[fila][col].setValor(num);
                if (resolver()) return true;
                grilla[fila][col].setValor(0);
            }
        }
        return false;
    }

    // ---------------- ACCESORES ----------------
    public int getValor(int fila, int col) {
        if (fila < 0 || fila > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Fila y columna deben estar entre 0 y 8.");
        }
        return grilla[fila][col].getValor();
    }

    public int[][] getGrillaSolucion() {
        int[][] solucion = new int[9][9];
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                solucion[f][c] = this.grilla[f][c].getValor();
            }
        }
        return solucion;
    }

    public Celda[][] getGrilla() {
        return grilla;
    }

    // ---------------- OPERACIONES ----------------
    public void llenarAleatoriamente() {
        GeneradorSudoku generador = new GeneradorSudoku();
        int celdasPrefijadas = 30;
        int[][] puzzle = generador.generar(celdasPrefijadas);

        inicializarGrilla(puzzle);
        notificarObservadores();
    }

    public void limpiar() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                grilla[f][c].setValor(0);
            }
        }
        notificarObservadores();
    }
}
