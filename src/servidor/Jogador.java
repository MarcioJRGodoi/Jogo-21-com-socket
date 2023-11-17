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
    
    public void mostrarMensagem() throws IOException {
    	out.println("Quer pegar uma carta?");
    }

    public int querCarta(Baralho baralho) throws IOException {
    	
         if(Integer.parseInt(in.readLine()) == 1) 
         {
        	 Carta carta = baralho.pegarCarta();
         	if(carta.getNome().contains("Ás")) 
         	{
         		asNaManga.add(carta);
         	}
         	if(asNaManga.size() > 0 && pontuacao > 10) {
         		pontuacao -= 10;
         		asNaManga.remove(0);
         	}
         	pontuacao += carta.getValor();
                 cartas.add(carta.getNome());
        	 return 1;
         }
         return 2;
    }

    public void mostrarCartas() {
    	out.println("--------------------------------------------------------");
        out.println("Suas Cartas"+ cartas);
    }

    public int getPontuacao() {
        return pontuacao;
    }
    
    public void mostrarCartasDaMesa(int numCartasAdversario) {
    	out.println("--------------------------------------------------------");
        out.println("Suas Cartas: " + cartas);
        out.println("Número de cartas do adversário: " + numCartasAdversario);
        out.println("--------------------------------------------------------");
        //out.println();
        //out.println("*");
    }
    
    public int getNumCartas() {
        return cartas.size();
    }
    
    public void resultado(int pontuacaoAdversario) {
    	if (pontuacao > pontuacaoAdversario && pontuacao <= 21) {
            out.println("Você ganhou!");
        } else if (pontuacaoAdversario > pontuacao && pontuacaoAdversario <= 21) {
            out.println("Você perdeu!");
        } else {
            out.println("Empate!");
        }
    	out.println("Sua pontuacao: " +getPontuacao() + " Pontuacao do Adversario: " +pontuacaoAdversario);
    	//out.println("*");
    }
    
    public void close() throws IOException {
		in.close();
		out.close();
		socket.close();

    }

}
