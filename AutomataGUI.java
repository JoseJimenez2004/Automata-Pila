import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class AutomataGUI extends JFrame {

    private Automata automata;
    private JTextArea automataInfoArea;
    private JTextField inputField;
    private JButton ejecutarButton;
    private JLabel resultLabel;

    public AutomataGUI() {
        setTitle("Automata con Pila");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de información del autómata
        automataInfoArea = new JTextArea();
        automataInfoArea.setEditable(false);
        automataInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        automataInfoArea.setBorder(BorderFactory.createTitledBorder("Información del Autómata"));
        JScrollPane scrollPane = new JScrollPane(automataInfoArea);

        // Panel de entrada del usuario
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel inputLabel = new JLabel("Inserte la cadena a probar:");
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputLabel.setFont(new Font("Serif", Font.BOLD, 14));
        inputField = new JTextField(20);
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        ejecutarButton = new JButton("Ejecutar Automata");
        ejecutarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ejecutarButton.setFont(new Font("Serif", Font.BOLD, 14));

        inputPanel.add(inputLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(inputField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(ejecutarButton);

        // Panel de resultados
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Serif", Font.BOLD, 16));
        resultLabel.setForeground(Color.BLUE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBorder(BorderFactory.createTitledBorder("Resultado"));

        // Añadir componentes al frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.SOUTH);

        // Acción del botón
        ejecutarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarAutomata();
            }
        });

        try {
            automata = new Automata();
            automata.mostrarInformacionAutomata();
            automataInfoArea.setText(getAutomataInfo());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el fichero del autómata", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarAutomata() {
        String input = inputField.getText();
        if (!input.endsWith("@")) {
            input += "@";
        }
        inputField.setText(input);
        automata.ejecutarAutomata(input);
        resultLabel.setText(automata.getResult());
    }

    private String getAutomataInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Estado inicial: ").append(automata.getEstadoInicial()).append("\n");
        info.append("Símbolo inicial de la pila: ").append(automata.getSimboloInicialPila()).append("\n");
        info.append("Conjunto de estados: ").append(automata.getConjuntoQ().toString()).append("\n");
        info.append("Alfabeto del lenguaje: ").append(automata.getConjuntoSigma().toString()).append("\n");
        info.append("Alfabeto de la pila: ").append(automata.getConjuntoR().toString()).append("\n");
        info.append("Conjunto de estados finales: ").append(automata.getConjuntoF().toString()).append("\n");
        info.append("Conjunto de transiciones:").append("\n");
        for (String[] transicion : automata.getConjuntoTransiciones()) {
            info.append(Arrays.toString(transicion)).append("\n");
        }
        return info.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AutomataGUI().setVisible(true);
            }
        });
    }
}

