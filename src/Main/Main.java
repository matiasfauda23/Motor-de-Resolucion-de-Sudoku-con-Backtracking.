package Main;

import interfaz.PantallaPrincipal;
import logica.Controlador;
import logica.Tablero;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        Controlador controlador = new Controlador(tablero);
        PantallaPrincipal vista = new PantallaPrincipal(tablero, controlador);
        vista.setVisible(true);

    }
}