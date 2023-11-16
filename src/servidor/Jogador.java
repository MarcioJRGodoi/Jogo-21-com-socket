package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Jogador {
	private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int pontuacao;
    private List<String> cartas;
    private List<Carta> asNaManga;

    public Jogador(Socket socket) throws IOException {
    	this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.pontuacao = 0;
        this.cartas = new ArrayList<>();
        this.asNaManga = new ArrayList<>();
    }

    public boolean querCarta() throws IOException {
        out.println("Quer pegar uma carta? 1 - Sim, 2 - Não");
        return Integer.parseInt(in.readLine()) == 1;
    }

    public void pegarCarta(Carta carta) {
        if(carta.getNome().contains("Ás")) {
            asNaManga.add(carta);
        }
        if(asNaManga.size() > 0 && pontuacao > 10) {
            pontuacao -= 10;
            asNaManga.remove(0);
        }
        pontuacao += carta.getValor();
        cartas.add(carta.getNome());
    }

    public void mostrarCartas() {
        out.println("Suas Cartas"+ cartas);
    }

    public int getPontuacao() {
        return pontuacao;
    }
    
    public void mostrarCartasAdversario(int numCartasAdversario) {
        out.println("Suas Cartas: " + cartas);
        out.println("Número de cartas do adversário: " + numCartasAdversario);
        out.println();
        out.println("*");
    }
    
    public int getNumCartas() {
        return cartas.size();
    }
    
    public void ganhou() {
    	out.println("Você ganhou!");
    }
    public void perdeu() {
    	out.println("Você perdeu!");
    }
    public void empatou() {
    	out.println("Empate!");
    }
    
    public void resultado(int pontuacao) {
    	out.println("Sua pontuacao: " +getPontuacao() + " Pontuacao do Adversario: " +pontuacao);
    	out.println("*");
    }
    
    public void close() throws IOException {
		in.close();
		out.close();
		socket.close();

    }

}
