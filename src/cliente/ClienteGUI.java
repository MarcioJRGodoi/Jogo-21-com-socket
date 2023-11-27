package cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClienteGUI {
    private JFrame frame;
    private JButton btnPegarCarta;
    private JButton btnNaoPegarCarta;
    private JButton btnNovoJogo;
    private JTextArea txtArea;
    private BufferedReader in;
    private PrintWriter out;
    private String url;

    public ClienteGUI(Socket socket, BufferedReader in, PrintWriter out, String url) {
        this.in = in;
        this.out = out;
        this.url = url;

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
                txtArea.append("Aguardando jogador adversario\n");
                btnPegarCarta.setEnabled(false);
                btnNaoPegarCarta.setEnabled(false);
            }
        });

        // Cria o JButton para n√£o pegar uma carta
        btnNaoPegarCarta = new JButton("N„o Pegar Carta");
        btnNaoPegarCarta.setBackground(Color.RED);
        btnNaoPegarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Envia a resposta para o servidor
                out.println("2");
                txtArea.append("Aguardando jogador adversario\n");
                btnPegarCarta.setEnabled(false);
                btnNaoPegarCarta.setEnabled(false);
            }
        });
        
        btnNovoJogo = new JButton("Novo Jogo");
        btnNovoJogo.setBackground(Color.BLUE);
        btnNovoJogo.setForeground(Color.WHITE);
        btnNovoJogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		socket.close();
            		Socket socket = new Socket(url, 9000);
            		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    new ClienteGUI(socket, in, out, url);
                	frame.dispose();                
                } catch (IOException exception) {
                	exception.printStackTrace();
                }
            	
            }
        });

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);

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
                    	if (mensagemDoServidor.equals("NOVO JOGO")) {
                            txtArea.setText("");
                            mensagemDoServidor = in.readLine();
                        }
                    	txtArea.append("Mensagem do servidor: " + mensagemDoServidor + "\n");
                    	btnPegarCarta.setEnabled(true);
                        btnNaoPegarCarta.setEnabled(true);
                    }
                    painelBotoes.add(btnNovoJogo);
                    btnPegarCarta.setVisible(false);
                    btnNaoPegarCarta.setVisible(false);  
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
