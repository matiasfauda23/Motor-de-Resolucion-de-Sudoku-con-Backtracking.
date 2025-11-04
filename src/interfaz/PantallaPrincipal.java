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
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(SwingConstants.CENTER);
                campo.setFont(new Font("Arial", Font.BOLD, 20));
                campo.setColumns(1);

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

                // Solo permitir dígitos del 1 al 9 y una cifra por celda
                if (!Character.isDigit(ch) || ch == '0' || campo.getText().length() >= 1) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // 🟨 Nuevo: Si presiona Backspace, borrar valor
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
            null,                        // sin componente padre → centrado en la pantalla
            "No es soluble",             // mensaje
            "Sudoku",                    // título de la ventana
            javax.swing.JOptionPane.WARNING_MESSAGE // icono de advertencia
        );
    }

	@Override
	public void noEsposibleRellenar() {
        javax.swing.JOptionPane.showMessageDialog(
                null,                        // sin componente padre → centrado en la pantalla
                "No es posible rellenar con 17 lugares, tiene libre " + _controlador.lugaresLibres(),             // mensaje
                "Sudoku",                    // título de la ventana
           javax.swing.JOptionPane.WARNING_MESSAGE // icono de advertencia
        );		
	}

	@Override
	public void yaEstaResuelto() {
        javax.swing.JOptionPane.showMessageDialog(
                null,                        // sin componente padre → centrado en la pantalla
                "El sudoku ya esta resuelto, limpielo para volver a intentar",             // mensaje
                "Sudoku",                    // título de la ventana
           javax.swing.JOptionPane.WARNING_MESSAGE // icono de advertencia
        );			
	}

}
