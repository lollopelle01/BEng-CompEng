import java.io.Serializable;

public class Stanza implements Serializable{
    private int K = 5;
    public String Nome = null ;
    public String Stato = null;
    public String[] Utenti = null;

    public Stanza(){
        this.Nome = "L";
        this.Stato = "L";
        Utenti = new String[K];
        for(int i=0; i<K; i++){
            this.Utenti[i] = "L";
        }
    }

    public void stampa(){
        System.out.print(Nome + "\t" + Stato + "\t");
        for(int i=0; i<K; i++){
            System.out.print(Utenti[i] + "\t");
        }
        System.out.print("\n");
    }
}
