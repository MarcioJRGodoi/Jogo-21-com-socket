package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Jogo implements Runnable {
    private Jogador jogador1;
    private String nomeJogador1;
    private Jogador jogador2;
    private String nomeJogador2;
    private Baralho baralho = new Baralho();

    public Jogo(Socket jogador1, String nomeJogador1, Socket jogador2, String nomeJogador2) throws IOException {
        this.jogador1 = new Jogador(jogador1);
        this.nomeJogador1 = nomeJogador1;
        this.jogador2 = new Jogador(jogador2);
        this.nomeJogador2 = nomeJogador2;
    }

    @Override
    public void run() {
        try {
        	jogador1.querCarta(baralho);
        	jogador2.querCarta(baralho);
        	jogador1.mostrarCartasDaMesa(jogador2.getNumCartas(), jogador2.primeiraCarta());
        	jogador2.mostrarCartasDaMesa(jogador1.getNumCartas(), jogador1.primeiraCarta());
            // Lida com o jogo aqui        
            while(true) {
            	jogador1.mostrarMensagem();
            	jogador2.mostrarMensagem();
            	boolean pegouCarta1 = jogador1.querCarta(baralho);            	
            	boolean pegouCarta2 = jogador2.querCarta(baralho);
            	
            	if(!pegouCarta1 && !pegouCarta2) {
                	break;
                }            	
            	jogador1.mostrarCartasDaMesa(jogador2.getNumCartas(), jogador2.primeiraCarta());
            	jogador2.mostrarCartasDaMesa(jogador1.getNumCartas(), jogador1.primeiraCarta());         	                    
            }            
            jogador1.resultado(jogador2.getPontuacao());
        	jogador2.resultado(jogador1.getPontuacao());
            
         }catch (IOException e) {
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