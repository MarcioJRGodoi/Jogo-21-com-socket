package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
            List<String> cartasJogador1 = new ArrayList<String>();
            List<String> cartasJogador2 = new ArrayList<String>();
            
            while(true) {
            	outJogador1.println("Quer pegar uma carta? 1 - Sim, 2 - Não");
                int numCartasJogador1 = Integer.parseInt(inJogador1.readLine());
                if (numCartasJogador1 == 1) {
                	Carta carta = baralho.pegarCarta();
                    pontuacaoJogador1 += carta.getValor();
                    cartasJogador1.add(carta.getNome());
                }
                
                outJogador2.println("Quer pegar uma carta? 1 - Sim, 2 - Não");
                int numCartasJogador2 = Integer.parseInt(inJogador2.readLine());
                if (numCartasJogador2 == 1) {
                	Carta carta = baralho.pegarCarta();
                    pontuacaoJogador2 += carta.getValor();
                    cartasJogador2.add(carta.getNome());
                }
                
                if(numCartasJogador1 != 1 && numCartasJogador2 != 1) {
                	break;
                }
                outJogador2.println("Suas Cartas"+ cartasJogador2);
                outJogador2.println("Numero de cartas do adversario: "+ cartasJogador1.size());
                outJogador2.println("*");
                outJogador1.println("Suas Cartas"+ cartasJogador1);
                outJogador1.println("Numero de cartas do adversario: "+ cartasJogador2.size());
                outJogador1.println("*");
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
            
            outJogador1.println("Sua pontuacao: " +pontuacaoJogador1 + " Pontuacao do Adversario: " +pontuacaoJogador2);
            outJogador1.println("*");
            outJogador2.println("Sua pontuacao: " +pontuacaoJogador2 + " Pontuacao do Adversario: " +pontuacaoJogador1);
            outJogador2.println("*");
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