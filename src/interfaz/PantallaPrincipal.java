package interfaz;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font; // Para la fuente
import javax.swing.SwingConstants; // Para centrar el texto


public class PantallaPrincipal extends JFrame {

	// Atrbutos de la clase
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; 
	private JTextField[][] grillaCampos; 
	private JPanel _panelGrilla;
	private JPanel panelBotones;
	
	// Los botones
	private JButton botonResolver;
	private JButton botonGenerar;
	private JButton botonLimpiar;


/*	public static void main(String[] args) {
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
	}*/

	//Constructor ventana principal
	public PantallaPrincipal() {
		
		//Configuracion de la ventana
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 259); 
		
		//Panel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new java.awt.BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		//Panel de la grilla (Centro)
		_panelGrilla = new JPanel();
		contentPane.add(_panelGrilla, java.awt.BorderLayout.CENTER);
		_panelGrilla.setLayout(new GridLayout(9, 9, 2, 2)); // 9x9
		
		// Inicializamos los campos de texto y los agregamos a la grilla
		inicializarCamposDeTexto();

		
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
	

	 // Metodo auxiliar privado para crear los campos de texto y agregarlos a la grilla
	 
	private void inicializarCamposDeTexto() {
		// Inicializo la matriz de campos de texto
		grillaCampos = new JTextField[9][9];
		
		// ciclo para agregar los campos de texto a la grilla
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				grillaCampos[f][c] = new JTextField();
				
				// Estilos 
				grillaCampos[f][c].setHorizontalAlignment(SwingConstants.CENTER);
				grillaCampos[f][c].setFont(new Font("Arial", Font.BOLD, 20));
				
				// Agrego el campo a la grilla
				_panelGrilla.add(grillaCampos[f][c]);
				
				limitarCaracterCelda(f, c);
				
				// Limitamos a un caracter por campo
				grillaCampos[f][c].setColumns(1);
			}
		}
	}




    
	public JButton getBotonResolver() {
        return botonResolver;
    }

    public JButton getBotonGenerar() {
        return botonGenerar;
    }

    public JButton getBotonLimpiar() {
        return botonLimpiar;
    }
    
    public int [][] getDatosDeGrilla(){
		int[][] datos = new int[9][9];
		for(int f=0; f<9; f++) {
			for(int c=0; c<9; c++) {
				String texto = grillaCampos[f][c].getText();
				if(texto.isEmpty()) {
					datos[f][c] = 0;
				}else {
					datos[f][c] = Integer.parseInt(texto);
				}
			}
		}
		return datos;
	}

    public void setDatosEnGrilla(int[][] solucion) {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                
                // Verificamos si la celda ya tenia un num
                boolean esPrefijada = !grillaCampos[f][c].getText().isEmpty();
                
                String valor = String.valueOf(solucion[f][c]);
                grillaCampos[f][c].setText(valor);
                grillaCampos[f][c].setEditable(false); 
                
                if (esPrefijada) {
                    grillaCampos[f][c].setForeground(Color.BLACK); 
                } else {
                    grillaCampos[f][c].setForeground(Color.BLUE); // Color azul
                }
            }
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
	public void limpiarTablero() {
	    for (int f = 0; f < 9; f++) {
	        for (int c = 0; c < 9; c++) {
	            grillaCampos[f][c].setText(""); 
	            grillaCampos[f][c].setEditable(true);
	            grillaCampos[f][c].setForeground(Color.BLACK); 
	        }
	    }
	}

	 private void limitarCaracterCelda(int f, int c) {
		//Agrego escuchador de letras
		grillaCampos[f][c].addKeyListener(new KeyAdapter() {
		
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar(); 
			JTextField campo = (JTextField) e.getSource(); 
			
			//Si la tecla no es un digito o es 0
			if (!Character.isDigit(c) || c == '0') {
				//Entonces ignoro la tecla
				e.consume();
				return;
			}
			//Si ya hay un caracter en el campo tambien lo ignoro
			if (campo.getText().length() >= 1) {
				e.consume();
			}
		}
});
	 }

}