package logica;

import java.awt.EventQueue;

import interfaz.PantallaPrincipal;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal vista = new PantallaPrincipal();
					new Controlador(vista); 
					vista.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
