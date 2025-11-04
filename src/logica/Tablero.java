package logica;

import java.util.ArrayList;
import java.util.Random;

public class Tablero {
    private Celda[][] celdas; // antes Caja[][] cajas
    private Validador validador = new Validador(this);
    private ArrayList<Observador> observadores = new ArrayList<>();

    
    // ---------------- CONSTRUCTOR ----------------
    public Tablero() {
        this.celdas = new Celda[9][9];
        inicializarEstructurasVacias();
    }
    
    
    // ---------------- INICIALIZACIÓN ----------------
    private void inicializarEstructurasVacias() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                celdas[fila][col] = new Celda(0, false);
            }
        }
    }

    
    // ---------------- OBSERVER ----------------
    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    private void notificarObservadores() {
        for (Observador o : observadores) {
            o.actualizar();
        }
    }
    
    private void noEsSolubleObservadores() {
        for (Observador o : observadores) {
            o.noSoluble();
        }
    }
    
    private void noEsPosibleRellenar() {
        for (Observador o : observadores) {
            o.noEsposibleRellenar();
        }
    }
    
    private void yaEstaResuelto() {
        for (Observador o : observadores) {
            o.yaEstaResuelto();
        }
    }

    
    // ---------------- CARGA MANUAL ----------------
    public void cargarDesdeMatriz(int[][] matriz) {
        if (matriz == null || matriz.length != 9)
            throw new IllegalArgumentException("La matriz debe tener 9 filas.");
        for (int[] fila : matriz)
            if (fila.length != 9)
                throw new IllegalArgumentException("Cada fila de la matriz debe tener 9 columnas.");

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = matriz[fila][col];
                if (valor < 0 || valor > 9)
                    throw new IllegalArgumentException("Los valores deben estar entre 0 y 9.");

                Celda celda = getCelda(fila, col);
                celda.setValor(valor);
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

            if (celda.getValor() == 0) {
                int valor = 1 + random.nextInt(9);
                if (validador.esMovimientoValido(fila, col, valor)) {
                    celda.setValor(valor);
                    celda.setEsPrefijada(false);
                    colocados++;
                }
            }
        }
        notificarObservadores();
    }

    
    // ---------------- BACKTRACKING ----------------
    public boolean resolver() {
        int[] pos = encontrarProximaCeldaVacia();
        if (pos == null) {
            notificarObservadores();
            return true;
        }

        int fila = pos[0];
        int col = pos[1];
        Celda celda = getCelda(fila, col);

        for (int num = 1; num <= 9; num++) {
            if (validador.esMovimientoValido(fila, col, num)) {
                celda.setValor(num);
                if (resolver()) return true;
                celda.setValor(0);
            }
        }
        return false;
    }

    
    // ---------------- BÚSQUEDA ----------------
    private int[] encontrarProximaCeldaVacia() {
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
        return celdas[fila][col].getValor();
    }

    public void setValor(int fila, int col, int valor) {
        validarCoordenadas(fila, col);
        Celda celda = celdas[fila][col];
        if (!celda.getEsPrefijada()) {
            celda.setValor(valor);
            celda.setEsPrefijada(true);
            notificarObservadores();
        }
    }
    
	public void borrarValor(int fila, int columna) {
        validarCoordenadas(fila, columna);
        Celda celda = celdas[fila][columna];
        celda.setValor(0);
        celda.setEsPrefijada(false);
        notificarObservadores();

	}

    public Celda getCelda(int fila, int col) {
        validarCoordenadas(fila, col);
        return celdas[fila][col];
    }

    
    // ---------------- VALIDACIONES ----------------
    private void validarCoordenadas(int fila, int col) {
        if (fila < 0 || fila > 8 || col < 0 || col > 8)
            throw new IllegalArgumentException("Fila y columna deben estar entre 0 y 8.");
    }

    public boolean esSoluble() {
        if (!validador.esTableroSoluble()) {
            noEsSolubleObservadores();
            return false;
        }
        return true;
    }
    
  
    // ---------------- FUNCIONES AUXILIARES ----------------
    public void limpiar() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = celdas[fila][col];
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
                solucion[fila][col] = celdas[fila][col].getValor();
            }
        }
        return solucion;
    }

	public int lugaresLibres() {
		int lugareslibres = 0;
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                Celda celda = celdas[fila][col];
                if (celda.getValor() == 0) {
                    lugareslibres++;
                }
            }
        }
        return lugareslibres;
	}

	public boolean tieneLugaresVacios() {
        if (this.lugaresLibres() == 0) {
            noEsPosibleRellenar();;
            return false;
        }
        return true;
	}

	public boolean estaResuelto() {
	    if(!validador.esTableroCompletoValido()) {
	    	yaEstaResuelto();
	    	return false;
	    }
	    return true;
	}


}
