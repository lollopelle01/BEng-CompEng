package beans;

import java.io.Serializable;
import java.time.Instant;

public class Offerta implements Serializable {

	private static final long serialVersionUID = 1L;

    private Utente acquirente;
    private int cifra;
    private Regalo regalo;
    private Instant time;
	
    
	// --- constructor ----------
	public Offerta(Utente acq, int c, Regalo r) {
        this.acquirente = acq;
        this.cifra = c;
        this.regalo = r;
        this.time = Instant.now();
	}

	// --- getters and setters --------------
	public Utente getAcquirente() {
		return acquirente;
	}
	public void setAcquirente(Utente acquirente) {
		this.acquirente = acquirente;
	}

	public int getCifra() {
		return cifra;
	}
	public void setCifra(int cifra) {
		this.cifra = cifra;
	}

	public Regalo getRegalo() {
		return regalo;
	}
	public void setRegalo(Regalo regalo) {
		this.regalo = regalo;
	}

	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
}
