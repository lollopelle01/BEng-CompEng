package beans;

//classe singleton
public class Partita {
	private boolean partita_avviata=false;
	 
	public boolean getPartita_avviata() {
		return partita_avviata;
	}

	public void setPartita_avviata(boolean b) {
		this.partita_avviata = b;
	}

	public Partita() {
		this.partita_avviata=true;
	}
}
