package beans;

public class Drink {
    private String nome;
    private float prezzo;
    private boolean consegnato;
    
    public Drink() { }

    public Drink(String nome, float prezzo, boolean consegnato) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.consegnato = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public boolean isConsegnato() {
        return consegnato;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }

}
