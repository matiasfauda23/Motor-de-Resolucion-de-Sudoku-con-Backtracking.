package logica;
import java.util.ArrayList;

public class Tablero {
    private Caja[][] cajas;
    private Validador validador = new Validador(this);
    private ArrayList<Observador> observadores = new ArrayList<>();

    // Constructores
    public Tablero(int[][] numeros) {
        this.cajas = new Caja[3][3];
        inicializarEstructuras(numeros);
    }

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
    private void inicializarEstructuras(int[][] numeros) {
        // Inicializar todas las cajas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cajas[i][j] = new Caja();
            }
        }

        // Inicializar las celdas y asignarlas a las cajas correspondientes
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int valor = numeros[fila][col];
                boolean esPrefijada = (valor != 0);
                Celda celda = new Celda(valor, esPrefijada);
                
                // Asignar a la caja correspondiente
                int indiceCajaFila = fila / 3;
                int indiceCajaCol = col / 3;
                int filaEnCaja = fila % 3;
                int colEnCaja = col % 3;
                
                cajas[indiceCajaFila][indiceCajaCol].setCelda(filaEnCaja, colEnCaja, celda);
            }
        }
    }

    // ---------------- LÓGICA DE SUDOKU BASADA EN CAJAS (REFACTORIZADA) ----------------
    public int[] encontrarProximaCeldaVacia() {
        for (int cajaFila = 0; cajaFila < 3; cajaFila++) {
            for (int cajaCol = 0; cajaCol < 3; cajaCol++) {
                Caja caja = cajas[cajaFila][cajaCol];
                // Delegar la búsqueda a la caja
                int[] resultado = caja.encontrarProximaCeldaVacia(cajaFila, cajaCol);
                if (resultado != null) {
                    return new int[]{resultado[0], resultado[1]}; // Solo devolver coordenadas globales
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
                setValor(fila, col, num);
                if (resolver()) return true;
                setValor(fila, col, 0);
            }
        }
        return false;
    }

    // ---------------- ACCESORES MÁS LIMPIOS ----------------
    public int getValor(int fila, int col) {
        validarCoordenadas(fila, col);
        Celda celda = getCelda(fila, col);
        return celda.getValor();
    }

    public void setValor(int fila, int col, int valor) {
        validarCoordenadas(fila, col);
        Celda celda = getCelda(fila, col);
        celda.setValor(valor);
        notificarObservadores();
    }

    public Celda getCelda(int fila, int col) {
        validarCoordenadas(fila, col);
        int indiceCajaFila = fila / 3;
        int indiceCajaCol = col / 3;
        int filaEnCaja = fila % 3;
        int colEnCaja = col % 3;
        
        return cajas[indiceCajaFila][indiceCajaCol].getCelda(filaEnCaja, colEnCaja);
    }

    // ---------------- MÉTODOS AUXILIARES ----------------
    private void validarCoordenadas(int fila, int col) {
        if (fila < 0 || fila > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Fila y columna deben estar entre 0 y 8.");
        }
    }

    // ---------------- MÉTODOS DE CONVERSIÓN ----------------
    public static int[] convertirACoordenadasCaja(int filaGlobal, int colGlobal) {
        return new int[]{
            filaGlobal / 3,  // cajaFila
            colGlobal / 3,   // cajaCol
            filaGlobal % 3,  // filaEnCaja
            colGlobal % 3    // colEnCaja
        };
    }

    public static int[] convertirACoordenadasGlobales(int cajaFila, int cajaCol, int filaEnCaja, int colEnCaja) {
        return new int[]{
            cajaFila * 3 + filaEnCaja,
            cajaCol * 3 + colEnCaja
        };
    }

    // ---------------- MÉTODOS DE OBTENCIÓN DE DATOS ----------------
    public int[][] getGrillaSolucion() {
        int[][] solucion = new int[9][9];
        for (int cajaFila = 0; cajaFila < 3; cajaFila++) {
            for (int cajaCol = 0; cajaCol < 3; cajaCol++) {
                Caja caja = cajas[cajaFila][cajaCol];
                for (int filaEnCaja = 0; filaEnCaja < 3; filaEnCaja++) {
                    for (int colEnCaja = 0; colEnCaja < 3; colEnCaja++) {
                        int filaGlobal = cajaFila * 3 + filaEnCaja;
                        int colGlobal = cajaCol * 3 + colEnCaja;
                        solucion[filaGlobal][colGlobal] = caja.getCelda(filaEnCaja, colEnCaja).getValor();
                    }
                }
            }
        }
        return solucion;
    }

    // Método para obtener una caja específica
    public Caja getCaja(int cajaFila, int cajaCol) {
        if (cajaFila < 0 || cajaFila > 2 || cajaCol < 0 || cajaCol > 2) {
            throw new IllegalArgumentException("Índices de caja deben estar entre 0 y 2.");
        }
        return cajas[cajaFila][cajaCol];
    }

    // Método para obtener la caja que contiene una celda específica
    public Caja getCajaDeCelda(int fila, int col) {
        int[] coordenadasCaja = convertirACoordenadasCaja(fila, col);
        return cajas[coordenadasCaja[0]][coordenadasCaja[1]];
    }

    public Caja[][] getCajas() {
        return cajas;
    }

    public Celda[][] getGrilla() {
        Celda[][] grilla = new Celda[9][9];
        for (int cajaFila = 0; cajaFila < 3; cajaFila++) {
            for (int cajaCol = 0; cajaCol < 3; cajaCol++) {
                Caja caja = cajas[cajaFila][cajaCol];
                for (int filaEnCaja = 0; filaEnCaja < 3; filaEnCaja++) {
                    for (int colEnCaja = 0; colEnCaja < 3; colEnCaja++) {
                        int filaGlobal = cajaFila * 3 + filaEnCaja;
                        int colGlobal = cajaCol * 3 + colEnCaja;
                        grilla[filaGlobal][colGlobal] = caja.getCelda(filaEnCaja, colEnCaja);
                    }
                }
            }
        }
        return grilla;
    }

    // ---------------- OPERACIONES ----------------
    public void llenarAleatoriamente() {
        GeneradorSudoku generador = new GeneradorSudoku();
        int celdasPrefijadas = 30;
        int[][] puzzle = generador.generar(celdasPrefijadas);
        inicializarEstructuras(puzzle);
        notificarObservadores();
    }

    public void limpiar() {
        for (int cajaFila = 0; cajaFila < 3; cajaFila++) {
            for (int cajaCol = 0; cajaCol < 3; cajaCol++) {
                Caja caja = cajas[cajaFila][cajaCol];
                for (int filaEnCaja = 0; filaEnCaja < 3; filaEnCaja++) {
                    for (int colEnCaja = 0; colEnCaja < 3; colEnCaja++) {
                        Celda celda = caja.getCelda(filaEnCaja, colEnCaja);
                        if (!celda.getEsPrefijada()) {
                            celda.setValor(0);
                        }
                    }
                }
            }
        }
        notificarObservadores();
    }

    public void reiniciar() {
        for (int cajaFila = 0; cajaFila < 3; cajaFila++) {
            for (int cajaCol = 0; cajaCol < 3; cajaCol++) {
                Caja caja = cajas[cajaFila][cajaCol];
                for (int filaEnCaja = 0; filaEnCaja < 3; filaEnCaja++) {
                    for (int colEnCaja = 0; colEnCaja < 3; colEnCaja++) {
                        caja.getCelda(filaEnCaja, colEnCaja).reiniciar();
                    }
                }
            }
        }
        notificarObservadores();
    }
}