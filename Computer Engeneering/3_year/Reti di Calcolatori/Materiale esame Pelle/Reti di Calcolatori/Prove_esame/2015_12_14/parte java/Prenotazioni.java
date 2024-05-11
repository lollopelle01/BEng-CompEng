import java.io.*;

public class Prenotazioni implements Serializable{
    
    public String identificatore = null;
    public String persone = null;
    public String tipo_prenotazione = null;
    public String tipo_veicolo = null;
    public String targa_veicolo = null;
    public String immagine = null;

    public Prenotazioni(){
        identificatore = "L";
        persone = "L";
        tipo_prenotazione = "L";
        tipo_veicolo = "L";
        targa_veicolo = "L";
        immagine = "L";
    }

    public void stampa(){
        System.out.println(identificatore + "\t" + persone + "\t" + tipo_prenotazione + "\t" + tipo_veicolo + "\t" + targa_veicolo + "\t" + immagine);
    }

    public void libera(){
        identificatore = "L";
        persone = "L";
        tipo_prenotazione = "L";
        tipo_veicolo = "L";
        targa_veicolo = "L";
        immagine = "L";
    }
}
