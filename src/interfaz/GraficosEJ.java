package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logica.Tablero;
import logica.Controlador;

public class GraficosEJ extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFreeChart _grafico;
	private DefaultCategoryDataset _datos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraficosEJ frame = new GraficosEJ();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraficosEJ() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ChartPanel panel = new ChartPanel(_grafico);
				panel.setBounds(10, 130, 414, 120);
				JFrame ventana = new JFrame("Gráfico de Sodokus - tiempo de ejecucion");
				ventana.getContentPane().add(panel);
				ventana.pack();
				ventana.setVisible(true);
			}
		});
		btnNewButton.setBounds(121, 82, 159, 31);
		contentPane.add(btnNewButton);
		
		_datos = new DefaultCategoryDataset();
				
		Tablero tablero = new Tablero();
		tablero.llenarAleatoriamente(10);
		Controlador controlador = new Controlador(tablero);
		controlador.resolverSudoku();
		
		Tablero tablero2 = new Tablero();
		tablero2.llenarAleatoriamente(5);
		Controlador controlador2 = new Controlador(tablero2);
		controlador2.resolverSudoku();
		
//		Tablero tablero3 = new Tablero();
//		tablero2.llenarAleatoriamente(5);
//		Controlador controlador3 = new Controlador(tablero2);
//		controlador2.resolverSudoku();
	
		
		
		
		_datos.addValue(tablero.getTiempoResolucion(), "Sodoku1", "sodoku2");
		_datos.addValue(tablero2.getTiempoResolucion(), "Sodoku2", "sodoku2");
//		_datos.addValue(tablero3.getTiempoResolucion(), "Sodoku2", "Domingo");
		
		_grafico = ChartFactory.createBarChart(
				"tiempo de ejecucion", // Título
				"Sodokus", // Etiqueta del eje X
				"cantidad de tiempo", // Etiqueta del eje Y
				_datos,
				PlotOrientation.VERTICAL,
				true,
				false,
				false
				);
		
		
	}
}
