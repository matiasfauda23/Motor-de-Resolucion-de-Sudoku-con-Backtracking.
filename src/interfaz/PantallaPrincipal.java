package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import logica.Controlador;
import logica.Tablero;
import logica.Observador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PantallaPrincipal extends JFrame implements Observador {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField[][] grillaCampos;
	private JPanel _panelGrilla;
	private JPanel panelBotones;

	private JButton _botonResolver;
	private JButton _botonGenerar;
	private JButton _botonMultiplesSodokus;
	private JButton _botonLimpiar;
	private JSpinner spinnerCeldas;

	private JButton _botonAnterior;
	private JButton _botonSiguiente;
	private JLabel _labelContador;
	
	private Controlador _controlador;
	private Tablero _tablero;
	
	private JFreeChart _grafico;
	private DefaultCategoryDataset _datos;

	public PantallaPrincipal(Tablero tablero, Controlador controlador) {
		_tablero = tablero;
		_controlador = controlador;

		inicializarInterfaz();
		
		// Vinculamos eventos a controlador
		configurarEventos();
		ocultarNavegacion();

		this.pack();
		this.setLocationRelativeTo(null);
		
	}
	
	private void inicializarInterfaz() {
		// La vista se suscribe al modelo
		_tablero.agregarObservador(this);
			
		setTitle("Sudoku Solver"); // boraa esta
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();              
		contentPane.setPreferredSize(new Dimension(1000, 800));                       
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		_panelGrilla = new JPanel(new GridLayout(9, 9, 2, 2));
		contentPane.add(_panelGrilla, BorderLayout.CENTER);
		inicializarCamposDeTexto();

		panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);

		_botonResolver = new JButton("Resolver");
		_botonGenerar = new JButton("Generar Aleatorio");
		_botonLimpiar = new JButton("Limpiar");
		_botonMultiplesSodokus = new JButton("Generar Múltiples Sudokus");
		
		mejorarBoton(_botonResolver);
		mejorarBoton(_botonGenerar);
		mejorarBoton(_botonLimpiar);
		mejorarBoton(_botonMultiplesSodokus);

		panelBotones.add(_botonResolver);
		panelBotones.add(_botonGenerar);
		panelBotones.add(_botonLimpiar);
		panelBotones.add(_botonMultiplesSodokus);
		
		JLabel labelCeldas = new JLabel("Celdas:");

		SpinnerModel model = new SpinnerNumberModel(15, 10, 20, 1); 
		spinnerCeldas = new JSpinner(model);

		panelBotones.add(labelCeldas);
		panelBotones.add(spinnerCeldas);

		_botonAnterior = new JButton("< Ant");
		_botonSiguiente = new JButton("Sig >");
		_labelContador = new JLabel("Solución 1 de 1");

		panelBotones.add(_botonAnterior);
		panelBotones.add(_labelContador);
		panelBotones.add(_botonSiguiente);
		
	}

	private void mejorarBoton(JButton boton) {
	    boton.setFont(new Font("Segoe UI", Font.ROMAN_BASELINE, 14));
	    boton.setBackground(new Color(59, 89, 182)); // Azul
	    boton.setForeground(Color.WHITE);
	    boton.setFocusPainted(false); // Quita el borde de foco
	    boton.setBorderPainted(false); // Quita el borde
	    boton.setOpaque(true);
	    boton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano
	    boton.setPreferredSize(new Dimension(210, 30));
	}

	private void configurarEventos() {
		_botonResolver.addActionListener(e -> _controlador.resolverSudoku());
		_botonGenerar.addActionListener(e -> {
			int cantidad = (int) spinnerCeldas.getValue();
			
			_controlador.generarSudoku(cantidad);
		});
		_botonMultiplesSodokus.addActionListener(e ->solicitarYGenerarMultiplesSudokus());

		_botonLimpiar.addActionListener(e -> _controlador.limpiarGrilla());
		_botonSiguiente.addActionListener(e -> _controlador.mostrarSiguienteSolucion());
		_botonAnterior.addActionListener(e -> _controlador.mostrarAnteriorSolucion());			
	}
	
	public void solicitarYGenerarMultiplesSudokus(){
		
	    String inputCantidad = JOptionPane.showInputDialog(
	        this,
	        "¿Cuántos sudokus desea generar?(2-20)",
	        "Generar Sudokus Aleatorios",
	        JOptionPane.QUESTION_MESSAGE
	    );
	    
	    if (inputCantidad != null) {
	        try {
	            int cantidad = Integer.parseInt(inputCantidad);
	            
	            String inputPrefijadas = JOptionPane.showInputDialog(
	                this,
	                "¿Cuántas celdas prefijadas? (30-81)",
	                "Celdas Prefijadas",
	                JOptionPane.QUESTION_MESSAGE
	            );
	            
	            if (inputPrefijadas != null) {
	                int numerosAColocar = Integer.parseInt(inputPrefijadas);
	                
	                // Llama al controlador 	               
	                mostrarEstadisticas(_controlador.generarMultiplesSudokus(cantidad, numerosAColocar), numerosAColocar);
	            }
	            
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(
	                this, 
	                "Por favor ingrese un número válido", 
	                "Error", 
	                JOptionPane.ERROR_MESSAGE
	            );
	        } catch (IllegalArgumentException ex) {
	            JOptionPane.showMessageDialog(
	                this, 
	                ex.getMessage(), 
	                "Error", 
	                JOptionPane.ERROR_MESSAGE
	            );
	        }
	    }	    			
	}

	private void mostrarEstadisticas(Tablero[] sudokus, int cantidadPrefijadas) {

	    _datos = new DefaultCategoryDataset();
	    for (int i = 0; i < sudokus.length; i++) {
	        String categoria = String.valueOf(cantidadPrefijadas);
	        _datos.addValue(
	            sudokus[i].getTiempoResolucion(), 
	            "Sudoku " + (i + 1), 
	            categoria
	        );

	    }

	    _grafico = ChartFactory.createBarChart(
	        "Tiempo de ejecución para encontrar todas las soluciones de " + sudokus.length + " sudokus",
	        "Celdas prefijadas",
	        "Tiempo (ms)",
	        _datos,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );

	    ChartPanel panel = new ChartPanel(_grafico);

	    JFrame ventana = new JFrame("Gráfico de Sudokus - Tiempo de ejecución");
	    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    ventana.getContentPane().add(panel);
	    ventana.setSize(1000, 600); 
	    ventana.setLocationRelativeTo(null);
	    ventana.setVisible(true);
	}

	private void inicializarCamposDeTexto() {
		grillaCampos = new JTextField[9][9];
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				JTextField campo = new JTextField();
				campo.setHorizontalAlignment(SwingConstants.CENTER);
				campo.setFont(new Font("Arial", Font.BOLD, 20));
				campo.setColumns(1);																
				
				campo.setBackground(_controlador.obtenerColorDeFondo(f, c));
                campo.setBorder(_controlador.crearBordeCelda(f, c));
                campo.setForeground(new Color(0, 0, 128));

				// Limitar entrada y notificar al controlador
				configurarCampo(campo, f, c);

				grillaCampos[f][c] = campo;
			
				_panelGrilla.add(campo);
			}
		}
	}

	private void configurarCampo(JTextField campo, int fila, int columna) {
		campo.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();

				// Solo permite dígitos del 1 al 9 y una cifra por celda
				if (!Character.isDigit(ch) || ch == '0' || campo.getText().length() >= 1) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					campo.setText("");
					_controlador.borrarValor(fila, columna);
					return;
				}

				String texto = campo.getText().trim();
				int valor = 0;
				
				if (!texto.isEmpty()) {
					try {
						valor = Integer.parseInt(texto);
						if (valor < 1 || valor > 9) {
							campo.setText("");
							return;
						}
					} catch (NumberFormatException ex) {
						campo.setText("");
						return;
					}
				}

				// Notificar al controlador del nuevo valor
				_controlador.setValor(fila, columna, valor);
			}
		});
	}
	

	@Override
	public void actualizar() {
		int[][] datos = _tablero.getGrillaSolucion();
		for (int f = 0; f < 9; f++) {
			for (int c = 0; c < 9; c++) {
				int val = datos[f][c];
				String texto = val == 0 ? "" : String.valueOf(val);
				if (!grillaCampos[f][c].getText().equals(texto)) {
					grillaCampos[f][c].setText(texto);					
				}
			}
		}
	}

	@Override
	public void noSoluble() {
		javax.swing.JOptionPane.showMessageDialog(
				null,                        
				"No es soluble",             
				"Sudoku",                    
				javax.swing.JOptionPane.WARNING_MESSAGE 
				);
	}

	@Override
	public void noEsposibleRellenar() {
		javax.swing.JOptionPane.showMessageDialog(
				null,
				"El tablero ya está lleno o no es posible llenarlo. Limpie la grilla para generar uno nuevo.",
				"Sudoku",
				javax.swing.JOptionPane.WARNING_MESSAGE
				);
	}

	@Override
	public void yaEstaResuelto() {
		javax.swing.JOptionPane.showMessageDialog(
				null,                        
				"El sudoku ya esta resuelto, limpielo para volver a intentar",           
				"Sudoku",                    
				javax.swing.JOptionPane.WARNING_MESSAGE 
				);			
	}

	@Override
	public void mostrarNavegacion(int indiceActual, int totalSoluciones) {
		_labelContador.setText("Solución " + (indiceActual + 1) + " de " + totalSoluciones);

		_botonAnterior.setEnabled(indiceActual > 0);
		_botonSiguiente.setEnabled(indiceActual < totalSoluciones - 1);

		
		_botonAnterior.setVisible(true);
		_botonSiguiente.setVisible(true);
		_labelContador.setVisible(true);
	}

	@Override
	public void ocultarNavegacion() {
		_botonAnterior.setVisible(false);
		_botonSiguiente.setVisible(false);
		_labelContador.setVisible(false);
	}

}
