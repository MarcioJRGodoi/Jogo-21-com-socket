package servidor;

import javax.swing.*;
import java.awt.*;

public class BlackjackGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton hitButton1;
    private JButton standButton1;
    private JButton hitButton2;
    private JButton standButton2;
    private JLabel playerHand1;
    private JLabel playerHand2;
    private JLabel dealerHand;

    public BlackjackGUI() {
        frame = new JFrame("Jogo 21");
        panel = new JPanel();
        hitButton1 = new JButton("Jogador 1 - Pedir");
        standButton1 = new JButton("Jogador 1 - Parar");
        hitButton2 = new JButton("Jogador 2 - Pedir");
        standButton2 = new JButton("Jogador 2 - Parar");
        playerHand1 = new JLabel("Mão do Jogador 1: ");
        playerHand2 = new JLabel("Mão do Jogador 2: ");
        dealerHand = new JLabel("Mão do Dealer: ");

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(dealerHand);
        panel.add(playerHand1);
        panel.add(hitButton1);
        panel.add(standButton1);
        panel.add(playerHand2);
        panel.add(hitButton2);
        panel.add(standButton2);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Jogo 21");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BlackjackGUI();
    }
}
