package beans;

import java.io.Serializable;

public class Risultato implements Serializable {

	private static final long serialVersionUID = 1L;

    private int semifinale;	
    private int vincitore;
    private int risultato;
    
    public Risultato(int s, int v, int r) {
    	this.risultato = r;
    	this.semifinale = s;
    	this.vincitore = v;
    }
    
	public int getSemifinale() {
		return semifinale;
	}
	public void setSemifinale(int semifinale) {
		this.semifinale = semifinale;
	}
	public int getVincitore() {
		return vincitore;
	}
	public void setVincitore(int vincitore) {
		this.vincitore = vincitore;
	}
	public int getRisultato() {
		return risultato;
	}
	public void setRisultato(int risultato) {
		this.risultato = risultato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
