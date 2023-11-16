package servidor;

public class Carta {
    private int valor;
    private String nome;

    public Carta(int valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public int getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }
}
