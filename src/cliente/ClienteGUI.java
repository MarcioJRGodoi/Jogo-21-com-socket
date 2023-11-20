package cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteGUI {
    private JFrame frame;
    private JButton btnPegarCarta;
    private JButton btnNaoPegarCarta;
    private JTextArea txtArea;
    private BufferedReader in;
    private PrintWriter out;

    public ClienteGUI(Socket socket, BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;

        // Cria o JFrame
        frame = new JFrame("21(BlackJack)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        ImageIcon icone = new ImageIcon("cliente/blackjack.jpg");
        frame.setIconImage(icone.getImage());
        
        // Cria o JButton para pegar uma carta
        btnPegarCarta = new JButton("Pegar Carta");
        btnPegarCarta.setBackground(Color.GREEN);
        btnPegarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Envia a resposta para o servidor
                out.println("1");
                btnPegarCarta.setEnabled(false);
                btnNaoPegarCarta.setEnabled(false);
            }
        });

        // Cria o JButton para não pegar uma carta
        btnNaoPegarCarta = new JButton("Não Pegar Carta");
        btnNaoPegarCarta.setBackground(Color.RED);
        btnNaoPegarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Envia a resposta para o servidor
                out.println("2");
                btnPegarCarta.setEnabled(false);
                btnNaoPegarCarta.setEnabled(false);
            }
        });

        txtArea = new JTextArea();
        txtArea.setEditable(false);

        // Adiciona os componentes ao JFrame
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnPegarCarta);
        painelBotoes.add(btnNaoPegarCarta);
        frame.getContentPane().add(BorderLayout.SOUTH, painelBotoes);
        frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(txtArea));

        frame.setVisible(true);

        // Inicia a thread de leitura
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mensagemDoServidor;
                    while ((mensagemDoServidor = in.readLine()) != null) {
                    	txtArea.append("Mensagem do servidor: " + mensagemDoServidor + "\n");
                    	btnPegarCarta.setEnabled(true);
                        btnNaoPegarCarta.setEnabled(true);
                    }
                    
                    btnPegarCarta.setEnabled(false);
                    btnNaoPegarCarta.setEnabled(false);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                finally 
                {
                	try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }).start();
    }
/*
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000); // Conecta ao servidor na porta 9000
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        new ClienteGUI(socket, in, out);
    }*/
}
