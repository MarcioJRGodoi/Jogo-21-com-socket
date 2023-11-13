package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Jogo implements Runnable {
    private Socket jogador1;
    private String nomeJogador1;
    private Socket jogador2;
    private String nomeJogador2;
    private Baralho baralho = new Baralho();

    public Jogo(Socket jogador1, String nomeJogador1, Socket jogador2, String nomeJogador2) {
        this.jogador1 = jogador1;
        this.nomeJogador1 = nomeJogador1;
        this.jogador2 = jogador2;
        this.nomeJogador2 = nomeJogador2;
    }

    @Override
    public void run() {
        try {
            // Lida com o jogo aqui
            BufferedReader inJogador1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
            PrintWriter outJogador1 = new PrintWriter(jogador1.getOutputStream(), true);
            BufferedReader inJogador2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
            PrintWriter outJogador2 = new PrintWriter(jogador2.getOutputStream(), true);

            int pontuacaoJogador1 = 0;
            int pontuacaoJogador2 = 0;

            outJogador1.println("Quantas cartas você quer?");
            int numCartasJogador1 = Integer.parseInt(inJogador1.readLine());
            for (int i = 0; i < numCartasJogador1; i++) {
                pontuacaoJogador1 += baralho.pegarCarta();
            }

            outJogador2.println("Quantas cartas você quer?");
            int numCartasJogador2 = Integer.parseInt(inJogador2.readLine());
            for (int i = 0; i < numCartasJogador2; i++) {
                pontuacaoJogador2 += baralho.pegarCarta();
            }
            
            if (pontuacaoJogador1 > pontuacaoJogador2 && pontuacaoJogador1 <= 21) {
                outJogador1.println("Você ganhou!");
                outJogador2.println("Você perdeu!");
            } else if (pontuacaoJogador2 > pontuacaoJogador1 && pontuacaoJogador2 <= 21) {
                outJogador1.println("Você perdeu!");
                outJogador2.println("Você ganhou!");
            } else {
                outJogador1.println("Empate!");
                outJogador2.println("Empate!");
            }
            
            outJogador1.println("Sua pontuacao: " +pontuacaoJogador1);
            outJogador1.println("Pontuacao do Adversario: " +pontuacaoJogador2);
            
            outJogador2.println("Sua pontuacao: " +pontuacaoJogador2);
            outJogador2.println("Pontuacao do Adversario: " +pontuacaoJogador1);
        } catch (IOException e) {
            System.out.println("Falha ao lidar com o jogo: " + e.getMessage());
        } finally {
            try {
                jogador1.close();
                jogador2.close();
            } catch (IOException e) {
                System.out.println("Não foi possível fechar os sockets dos jogadores");
            }
        }
    }
}