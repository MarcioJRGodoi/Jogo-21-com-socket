package principal;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000); // Cria um ServerSocket na porta 9000

        while (true) {
            Socket jogador1 = serverSocket.accept(); // Aceita a conexão do jogador 1
            System.out.println("Jogador 1 Conectado");
            Socket jogador2 = serverSocket.accept(); // Aceita a conexão do jogador 2
            System.out.println("Jogador 2 Conectado");
            // Cria uma nova thread para cada jogo
            new Thread(new Jogo(jogador1, "Jogador 1", jogador2, "Jogador 2")).start();
        }
    }
}