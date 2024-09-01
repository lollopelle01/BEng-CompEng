package beans;

public class Risultato {
	
	private boolean valore;
	private int somma;
	
	public Risultato(boolean valore, int somma) {
		super();
		this.valore = valore;
		this.somma = somma;
	}
	public boolean getValore() {
		return valore;
	}
	public void setValore(boolean valore) {
		this.valore = valore;
	}
	public int getSomma() {
		return somma;
	}
	public void setSomma(int somma) {
		this.somma = somma;
	}

}
