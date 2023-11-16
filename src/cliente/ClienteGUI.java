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
    private JButton botaoPegarCarta;
    private JButton botaoNaoPegarCarta;
    private JTextArea areaTexto;
    private BufferedReader in;
    private PrintWriter out;

    public ClienteGUI(Socket socket, BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;

        // Cria o JFrame
        frame = new JFrame("21(BlackJack)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Cria o JButton para pegar uma carta
        botaoPegarCarta = new JButton("Pegar Carta");
        botaoPegarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Envia a resposta para o servidor
                out.println("1");
            }
        });

        // Cria o JButton para não pegar uma carta
        botaoNaoPegarCarta = new JButton("Não Pegar Carta");
        botaoNaoPegarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Envia a resposta para o servidor
                out.println("2");
            }
        });

        // Cria a JTextArea
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);

        // Adiciona os componentes ao JFrame
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoPegarCarta);
        painelBotoes.add(botaoNaoPegarCarta);
        frame.getContentPane().add(BorderLayout.SOUTH, painelBotoes);
        frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(areaTexto));

        // Torna o JFrame visível
        frame.setVisible(true);

        // Inicia a thread de leitura
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mensagemDoServidor;
                    while ((mensagemDoServidor = in.readLine()) != null) {
                        areaTexto.append("Mensagem do servidor: " + mensagemDoServidor + "\n");
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000); // Conecta ao servidor na porta 9000
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        new ClienteGUI(socket, in, out);
    }
}
