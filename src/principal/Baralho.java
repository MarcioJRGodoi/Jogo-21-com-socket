package principal;

import java.util.Random;

class Baralho {
    private Random random = new Random();

    public int pegarCarta() {
        return random.nextInt(10) + 1; // Retorna um número entre 1 e 10
    }
}