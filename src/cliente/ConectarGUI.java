package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ConectarGUI {
    private JFrame frame;
    private JTextField txtField;
    private JButton btnConectar;

    public ConectarGUI() {
        // Cria o JFrame
        frame = new JFrame("Conectar ao servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 100);
        frame.setLocationRelativeTo(null);

        ImageIcon icone = new ImageIcon("cliente/blackjack.jpg");
        frame.setIconImage(icone.getImage());
        
        // Cria o JTextField para a URL do servidor
        txtField = new JTextField();
        txtField.setColumns(30);

        // Cria o JButton para conectar
        btnConectar = new JButton("Conectar");
        
        Action conectarAcao = new AbstractAction() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e) {
                btnConectar.doClick();
            }
        };

        // Mapeia a tecla Enter para a ação de conectar
        btnConectar.getActionMap().put("conectar", conectarAcao);
        btnConectar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "conectar");
        
        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Conecta ao servidor e inicia a GUI do cliente
                try {
                    Socket socket = new Socket(txtField.getText(), 9000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    new ClienteGUI(socket, in, out, txtField.getText());
                    frame.dispose();
                    //frame.setVisible(false); // Esconde a janela de configuração
                } catch (UnknownHostException ex) {
                    JOptionPane.showMessageDialog(frame, "URL do servidor não encontrada");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao conectar ao servidor");
                }
            }
        });

        // Adiciona os componentes ao JFrame
        JPanel painel = new JPanel();
        painel.add(new JLabel("URL do Servidor:"));
        painel.add(txtField);
        painel.add(btnConectar);
        frame.getContentPane().add(BorderLayout.CENTER, painel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ConectarGUI();
    }
}
