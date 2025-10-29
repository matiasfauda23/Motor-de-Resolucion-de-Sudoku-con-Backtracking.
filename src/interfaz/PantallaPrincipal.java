package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import logica.Controlador;
import logica.Tablero;
import logica.Observador;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PantallaPrincipal extends JFrame implements Observador {

    private JPanel contentPane; 
    private JTextField[][] grillaCampos; 
    private JPanel _panelGrilla;
    private JPanel panelBotones;

    private JButton _botonResolver;
    private JButton _botonGenerar;
    private JButton _botonLimpiar;

    private Controlador _controlador;
    private Tablero _tablero;

    public PantallaPrincipal(Tablero tablero, Controlador controlador) {
        _tablero = tablero;
        _controlador = controlador;

        // La vista se suscribe al modelo
        _tablero.agregarObservador(this);

        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 395, 259);

        contentPane = new JPanel();
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

        panelBotones.add(_botonResolver);
        panelBotones.add(_botonGenerar);
        panelBotones.add(_botonLimpiar);

        // Vinculamos eventos a controlador
        configurarEventos();

        this.pack(); 
        this.setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        _botonResolver.addActionListener(e -> _controlador.resolverSudoku());
        _botonGenerar.addActionListener(e -> _controlador.generarSudoku());
        _botonLimpiar.addActionListener(e -> _controlador.limpiarGrilla());
    }

    private void inicializarCamposDeTexto() {
        grillaCampos = new JTextField[9][9];
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                grillaCampos[f][c] = new JTextField();
                grillaCampos[f][c].setHorizontalAlignment(SwingConstants.CENTER);
                grillaCampos[f][c].setFont(new Font("Arial", Font.BOLD, 20));
                grillaCampos[f][c].setColumns(1);
                limitarCaracterCelda(grillaCampos[f][c]);
                _panelGrilla.add(grillaCampos[f][c]);
            }
        }
    }

    private void limitarCaracterCelda(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isDigit(ch) || ch == '0' || campo.getText().length() >= 1) {
                    e.consume();
                }
            }
        });
    }

    @Override
    public void actualizar() {
        int[][] datos = _tablero.getGrillaSolucion();
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int val = datos[f][c];
                grillaCampos[f][c].setText(val == 0 ? "" : String.valueOf(val));
            }
        }
    }

    public int[][] getDatosDeGrilla() {
        int[][] datos = new int[9][9];
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                String texto = grillaCampos[f][c].getText();
                datos[f][c] = texto.isEmpty() ? 0 : Integer.parseInt(texto);
            }
        }
        return datos;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
