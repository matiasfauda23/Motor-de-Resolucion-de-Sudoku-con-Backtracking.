package logica;

public interface Observador {
	void actualizar();
	
	void noSoluble();
	
	void noEsposibleRellenar();
	
	void yaEstaResuelto();
	
	void mostrarNavegacion(int indiceActual, int totalSoluciones);
	
	void ocultarNavegacion();
}
