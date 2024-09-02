public class Treno {
    private String id, tipo, partenza, arrivo, audio;
    private int hh, mm, ritardo;

    public Treno(String id, String tipo, String partenza, String arrivo, String audio, int hh, int mm, int ritardo) {
        this.id = id;
        this.tipo = tipo;
        this.partenza = partenza;
        this.arrivo = arrivo;
        this.audio = audio;
        this.hh = hh;
        this.mm = mm;
        this.ritardo = ritardo;
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }

    public synchronized String getTipo() {
        return tipo;
    }

    public synchronized void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public synchronized String getPartenza() {
        return partenza;
    }

    public synchronized void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public synchronized String getArrivo() {
        return arrivo;
    }

    public synchronized void setArrivo(String arrivo) {
        this.arrivo = arrivo;
    }

    public synchronized String getAudio() {
        return audio;
    }

    public synchronized void setAudio(String audio) {
        this.audio = audio;
    }

    public synchronized int getHh() {
        return hh;
    }

    public synchronized void setHh(int hh) {
        this.hh = hh;
    }

    public synchronized int getMm() {
        return mm;
    }

    public synchronized void setMm(int mm) {
        this.mm = mm;
    }

    public synchronized int getRitardo() {
        return ritardo;
    }

    public synchronized void setRitardo(int ritardo) {
        this.ritardo = ritardo;
    }

    public synchronized void stampa(){
        System.out.println("---------------------------------------");
        System.out.println(this.id);
        System.out.println(this.tipo);
        System.out.println(this.partenza);
        System.out.println(this.arrivo);
        System.out.println(this.hh + ":" + this.mm);
        System.out.println(this.ritardo);
        System.out.println(this.audio);

    }
}
