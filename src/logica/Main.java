package logica;

import java.awt.EventQueue;

import interfaz.PantallaPrincipal;

public class Main {
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
        Controlador controlador = new Controlador(tablero);
        PantallaPrincipal vista = new PantallaPrincipal(tablero, controlador);
        vista.setVisible(true);

    }
}