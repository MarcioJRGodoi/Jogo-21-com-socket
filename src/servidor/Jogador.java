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

    public boolean querCarta(Baralho baralho) throws IOException {
        if(cartas.isEmpty() || Integer.parseInt(in.readLine()) == 1) {
            Carta carta = baralho.pegarCarta();
            if(carta.getNome().contains("Ás")) {
                asNaManga.add(carta);
            }
            pontuacao += carta.getValor();
            cartas.add(carta.getNome());
                if(asNaManga.size() > 0 && (pontuacao - asNaManga.get(0).getValor()) > 10) {
                    pontuacao -= 10;
                    asNaManga.remove(0);
            }
            return true;
        }
        return false;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String primeiraCarta() {
        return cartas.get(0);
    }

    public void mostrarCartasDaMesa(int numCartasAdversario, String cartaadv) {
    	out.println("NOVO JOGO");
        out.println("--------------------------------------------------------");
        out.println("Suas Cartas: " + cartas);
        out.println("Número de cartas do adversário: " + numCartasAdversario);
        out.println("Carta aberta adversario: " + cartaadv);
        out.println("--------------------------------------------------------");
    }

    public int getNumCartas() {
        return cartas.size();
    }

    public void resultado(int pontuacaoAdversario) {
        if (pontuacao > 21) {
            if (pontuacaoAdversario > 21) {
                out.println("Empate!");
            } else {
                out.println("Você perdeu!");
            }
        } else if (pontuacaoAdversario > 21 || pontuacao > pontuacaoAdversario) {
            out.println("Você ganhou!");
        } else if (pontuacao == pontuacaoAdversario) {
            out.println("Empate!");
        } else {
            out.println("Você perdeu!");
        }
        out.println("Sua pontuacao: " + getPontuacao() + "\nPontuacao do Adversario: " + pontuacaoAdversario);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
