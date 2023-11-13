package principal;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000); // Conecta ao servidor na porta 9000

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String mensagemDoServidor;
        while ((mensagemDoServidor = in.readLine()) != null) {
            System.out.println("Mensagem do servidor: " + mensagemDoServidor);

            if ("Quantas cartas você quer?".equals(mensagemDoServidor)) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
                String numCartas = leitor.readLine();
                out.println(numCartas);
            } else if ("Você ganhou!".equals(mensagemDoServidor) || "Você perdeu!".equals(mensagemDoServidor) || "Empate!".equals(mensagemDoServidor)) {
                break;
            }
        }

        socket.close();
    }
}