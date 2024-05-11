package esame.jdbc;

import java.io.Serializable;

public class Partecipazione implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long studente;
	private long gita;
	
	public Partecipazione() {
		
	}
	
	public Partecipazione (long studente, long gita) {
		this.studente = studente;
		this.gita = gita;
	}
	
	public long getStudente() {
		return studente;
	}
	public void setStudente(long studente) {
		this.studente = studente;
	}
	public long getGita() {
		return gita;
	}
	public void setGita(long gita) {
		this.gita = gita;
	}
	
	
}
