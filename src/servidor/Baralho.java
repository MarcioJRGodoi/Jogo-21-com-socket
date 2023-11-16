package servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Baralho {
    private List<Carta> cartas;
    private Random random = new Random();

    public Baralho() {
        String[] naipes = {"Espadas", "Copas", "Ouros", "Paus"};
        String[] valores = {"Ás", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Dama", "Rei"};

        cartas = new ArrayList<Carta>();

        for (String naipe : naipes) {
            for (int i = 0; i < valores.length; i++) {
                String nome = valores[i] + " de " + naipe;
                int valor;
                if (i == 0) { // Ás
                    valor = 11; // Pode ser 1 ou 11
                } else if (i >= 10) { // Valete, Dama, Rei
                    valor = 10;
                } else { // Cartas numeradas
                    valor = i + 1;
                }
                cartas.add(new Carta(valor, nome));
            }
        }
    }

    public Carta pegarCarta() {
        int indice = random.nextInt(cartas.size());
        Carta carta = cartas.get(indice);
        cartas.remove(indice);
        return carta;
    }
}
