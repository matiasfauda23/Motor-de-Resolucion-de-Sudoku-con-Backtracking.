package interfaz;

import java.awt.EventQueue; // Importante para iniciar Swing
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font; // Para la fuente
import javax.swing.SwingConstants; // Para centrar el texto


public class PantallaPrincipal extends JFrame {

	// Atrbutos de la clase
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; 
	private JTextField[][] grillaCampos; 
	private JPanel panelGrilla;
	private JPanel panelBotones;
	
	// Los botones
	private JButton botonResolver;
	private JButton botonGenerar;
	private JButton botonLimpiar;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal frame = new PantallaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Constructor ventana principal
	public PantallaPrincipal() {
		
		//Configuracion de la ventana
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450); 
		
		//Panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new java.awt.BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		//Panel de la grilla (Centro)
		panelGrilla = new JPanel();
		contentPane.add(panelGrilla, java.awt.BorderLayout.CENTER);
		panelGrilla.setLayout(new GridLayout(9, 9, 2, 2)); // 9x9
		
		// Inicializamos la matriz de campos de texto
		grillaCampos = new JTextField[9][9];
		
		// Loop para agregar los campos de texto a la grilla
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				grillaCampos[f][c] = new JTextField();
				
				// Estilos para que se vea mejor
				grillaCampos[f][c].setHorizontalAlignment(SwingConstants.CENTER);
				grillaCampos[f][c].setFont(new Font("Arial", Font.BOLD, 20));
				
				// Agregamos el campo recién creado al panel de la grilla
				panelGrilla.add(grillaCampos[f][c]);
				// Limitamos a un caracter por campo
				grillaCampos[f][c].setColumns(1); 
			}
		}

		
		//Panel de botones (Abajo)
		panelBotones = new JPanel();
		contentPane.add(panelBotones, java.awt.BorderLayout.SOUTH);
		
		botonResolver = new JButton("Resolver");
		panelBotones.add(botonResolver);
		
		botonGenerar = new JButton("Generar Aleatorio");
		panelBotones.add(botonGenerar);
		
		botonLimpiar = new JButton("Limpiar");
		panelBotones.add(botonLimpiar);
		
		
		this.pack(); 
		
		// Centra la ventana en la pantalla
		this.setLocationRelativeTo(null); 
	}

}