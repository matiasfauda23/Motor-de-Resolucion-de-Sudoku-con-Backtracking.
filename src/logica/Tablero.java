package logica;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Tablero {
	private Celda[][] celdas;
	private Validador validador = new Validador(this);
	private ArrayList<Observador> observadores = new ArrayList<>();

	private ArrayList<int[][]> _soluciones;
	private int _indiceSolucionActual;
	private final int LIMITE_SOLUCIONES = 2;
	private double TiempoResolucion;

	//Constructor
	public Tablero() {
		this.celdas = new Celda[9][9];
		inicializarEstructurasVacias();
	}


	//Inicializacion de la grilla
	private void inicializarEstructurasVacias() {
		for (int fila = 0; fila < 9; fila++) {
			for (int col = 0; col < 9; col++) {
				celdas[fila][col] = new Celda(0, false);
			}
		}
	}


	//Observer
	public void agregarObservador(Observador o) {
		observadores.add(o);
	}

	private void notificarObservadores() {
		for (Observador o : observadores) {
			o.actualizar();
		}
	}

	public void noEsSolubleObservadores() {
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

	void notificarConteoSoluciones() {
		if (_soluciones == null) return;
		for (Observador o : observadores) {
			o.mostrarNavegacion(_indiceSolucionActual, _soluciones.size());
		}
	}
	private void notificarOcultarNavegacion() {
		for (Observador o : observadores) {
			o.ocultarNavegacion();
		}
	}


	//Cargar desde matriz
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


	//Llenar celdas aleatoriamente
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


	// Algoritmo de Backtracking para resolver el Sudoku
	public boolean resolverBacktrack() {
		int[] pos = encontrarProximaCeldaVacia();
		
		// Caso base: si no hay mas celdas vacias entonces encontramos solucion
		if (pos == null) {
			// Guardo copia de la solucion que encontre
			_soluciones.add(this.getGrillaSolucion());
			return _soluciones.size() >= LIMITE_SOLUCIONES;
		}

		int fila = pos[0];
		int col = pos[1];
		Celda celda = getCelda(fila, col);

		// Caso recursivo
		for (int num = 1; num <= 9; num++) {
			if (validador.esMovimientoValido(fila, col, num)) {
				celda.setValor(num);

				if (resolverBacktrack()) {
					return true; 
				}
				celda.setValor(0);
			}
		}
		return false;
	}
	
	//Multiples Sudokus
	
	public Tablero[] generarMultiplesSudokus(int cantidad, int numerosAColocar) {
	    if (cantidad < 2 || cantidad > 20) {
	        throw new IllegalArgumentException("La cantidad debe estar entre 2 y 20");
	    }

	    if (numerosAColocar < 30 || numerosAColocar > 81) {
	        throw new IllegalArgumentException("Las celdas prefijadas deben estar entre 30 y 81");
	    }
		
	    Tablero[] sudokusGenerados = new Tablero[cantidad];
	    int cantPrefijas = numerosAColocar;
	    
	    for (int i = 0; i < cantidad; i++) {	        
	        if(cantPrefijas > 0) {
	        	cantPrefijas -= 3;// n-3 para asegurar variedad en las prefijadas
	        }
	        Tablero nuevoTablero = new Tablero();
	        nuevoTablero.llenarAleatoriamente(cantPrefijas); 
	        	        
	        long tiempoInicio = System.nanoTime();
	        nuevoTablero.encontrarTodasLasSoluciones(); 
	        long tiempoFin = System.nanoTime();
	        
	        double tiempoEnMilisegundos = (tiempoFin - tiempoInicio) / 1_000_000.0;
	        nuevoTablero.setTiempoResolucion(tiempoEnMilisegundos);
	        
	        sudokusGenerados[i] = nuevoTablero;
	    }
	    return sudokusGenerados;
	}

	//Busqueda de celdas vacias
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
	public void encontrarTodasLasSoluciones() {
		this._soluciones = new ArrayList<>();
		this._indiceSolucionActual = -1;
		resolverBacktrack(); // Llama al método (que ahora se auto-limita)
	}

	//Getters y Setters
	public int getValor(int fila, int col) {
		validarCoordenadas(fila, col);
		return celdas[fila][col].getValor();
	}

	public void setValor(int fila, int col, int valor) {
		validarCoordenadas(fila, col);
		Celda celda = celdas[fila][col];
		if (!celda.getEsPrefijada()) {
			celda.setValor(valor);
		//	celda.setEsPrefijada(true);
			notificarObservadores();
		}
	}
	
	public double getTiempoResolucion() {
		return TiempoResolucion;
	}
	
	public void setTiempoResolucion(double tiempo) {
		TiempoResolucion= tiempo;
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

	public void cargarSolucion(int indice) {
		if (_soluciones == null || indice < 0 || indice >= _soluciones.size()) {
			return;
		}

		int[][] solucionACargar = _soluciones.get(indice);
		this._indiceSolucionActual = indice;

		for (int fila = 0; fila < 9; fila++) {
			for (int col = 0; col < 9; col++) {
				int valorDeSolucion = solucionACargar[fila][col];
				Celda celdaDelTablero = this.getCelda(fila, col);

				celdaDelTablero.setValor(valorDeSolucion);
			}
		}
		notificarObservadores();
	}

	public int getCantidadSoluciones() {
		return (_soluciones != null) ? _soluciones.size() : 0;
	}

	public int getIndiceSolucionActual() {
		return this._indiceSolucionActual;
	}


	//Validaciones
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


	//Funciones auxiliares
	public void limpiar() {
		for (int fila = 0; fila < 9; fila++) {
			for (int col = 0; col < 9; col++) {
				Celda celda = celdas[fila][col];
				if (!celda.getEsPrefijada()) {
					celda.setValor(0);
				}
			}
		}
		// Reiniciar la lista de soluciones
		this._soluciones = null;
		this._indiceSolucionActual = -1;
		this.TiempoResolucion=0;
		notificarOcultarNavegacion();
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
		if(validador.esTableroCompletoValido()) {
			yaEstaResuelto();
			return true;
		}
		return false;
	}
	
	//Bordes de las celdas
	public Border crearBordeCelda(int fila, int col) {
        int sup = (fila % 3 == 0) ? 3 : 1;
        int izq = (col % 3 == 0) ? 3 : 1;
        int inf = (fila == 8) ? 3 : 1;
        int der = (col == 8) ? 3 : 1;

        return BorderFactory.createMatteBorder(sup, izq, inf, der, Color.BLACK);
    }

}
