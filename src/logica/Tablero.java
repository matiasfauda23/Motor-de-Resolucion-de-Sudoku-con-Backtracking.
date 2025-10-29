package logica;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
    private Caja[][] cajas;
    private Validador validador = new Validador(this);
    private ArrayList<Observador> observadores = new ArrayList<>();

    // ---------------- CONSTRUCTORES ----------------
    public Tablero() {
        this.cajas = new Caja[3][3];
        inicializarEstructurasVacias();
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
    private void inicializarEstructurasVacias() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cajas[i][j] = new Caja();
            }
        }

        // todas las celdas vacías (valor 0, no prefijadas)
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = new Celda(0, false);
                int indiceCajaFila = fila / 3;
                int indiceCajaCol = col / 3;
                int filaEnCaja = fila % 3;
                int colEnCaja = col % 3;
                cajas[indiceCajaFila][indiceCajaCol].setCelda(filaEnCaja, colEnCaja, celda);
            }
        }
    }
    
 // ---------------- CARGA MANUAL ----------------
    public void cargarDesdeMatriz(int[][] matriz) {
        if (matriz == null || matriz.length != 9) {
            throw new IllegalArgumentException("La matriz debe tener 9 filas.");
        }
        for (int i = 0; i < 9; i++) {
            if (matriz[i].length != 9) {
                throw new IllegalArgumentException("Cada fila de la matriz debe tener 9 columnas.");
            }
        }

        // Recorremos todas las posiciones y actualizamos las celdas
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = matriz[fila][col];
                if (valor < 0 || valor > 9) {
                    throw new IllegalArgumentException("Los valores deben estar entre 0 y 9.");
                }

                Celda celda = getCelda(fila, col);
                celda.setValor(valor);

                // Si el valor es distinto de 0, marcamos como prefijada
                celda.setEsPrefijada(valor != 0);
            }
        }

        notificarObservadores();
    }

    // ---------------- LLENADO ALEATORIO ----------------
    public void llenarAleatoriamente(int cantidadInicial) {
        Random random = new Random();
        int colocados = 0;

        while (colocados < cantidadInicial) {
            int fila = random.nextInt(9);
            int col = random.nextInt(9);
            Celda celda = getCelda(fila, col);

            // solo rellenar si está vacía
            if (celda.getValor() == 0) {
                int valor = 1 + random.nextInt(9);
                if (validador.esMovimientoValido(fila, col, valor)) {
                    celda.setValor(valor);
                    celda.setEsPrefijada(true);
                    colocados++;
                }
            }
        }

        notificarObservadores();
    }

    // ---------------- BACKTRACKING ----------------
    public boolean resolver() {
        int[] pos = encontrarProximaCeldaVacia();
        if (pos == null) return true; // tablero completo → solución encontrada

        int fila = pos[0];
        int col = pos[1];
        Celda celda = getCelda(fila, col);

        for (int num = 1; num <= 9; num++) {
            if (validador.esMovimientoValido(fila, col, num)) {
                celda.setValor(num);
                if (resolver()) return true;
                celda.setValor(0); // backtrack
            }
        }
        return false; // sin solución en esta rama
    }

    // ---------------- BÚSQUEDA ----------------
    public int[] encontrarProximaCeldaVacia() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = getCelda(fila, col);
                if (celda.getValor() == 0 && !celda.getEsPrefijada()) {
                    return new int[]{fila, col};
                }
            }
        }
        return null;
    }

    // ---------------- ACCESORES ----------------
    public int getValor(int fila, int col) {
        validarCoordenadas(fila, col);
        return getCelda(fila, col).getValor();
    }

    public void setValor(int fila, int col, int valor) {
        validarCoordenadas(fila, col);
        Celda celda = getCelda(fila, col);
        if (!celda.getEsPrefijada()) {
            celda.setValor(valor);
            notificarObservadores();
        }
    }

    public Celda getCelda(int fila, int col) {
        validarCoordenadas(fila, col);
        int indiceCajaFila = fila / 3;
        int indiceCajaCol = col / 3;
        int filaEnCaja = fila % 3;
        int colEnCaja = col % 3;
        return cajas[indiceCajaFila][indiceCajaCol].getCelda(filaEnCaja, colEnCaja);
    }

    // ---------------- VALIDACIONES ----------------
    private void validarCoordenadas(int fila, int col) {
        if (fila < 0 || fila > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Fila y columna deben estar entre 0 y 8.");
        }
    }

    // ---------------- FUNCIONES AUXILIARES ----------------
    public void limpiar() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = getCelda(fila, col);
                if (!celda.getEsPrefijada()) {
                    celda.setValor(0);
                }
            }
        }
        notificarObservadores();
    }

    public int[][] getGrillaSolucion() {
        int[][] solucion = new int[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                solucion[fila][col] = getCelda(fila, col).getValor();
            }
        }
        return solucion;
    }
}
