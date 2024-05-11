package beans;

import java.time.Instant;

public class Utente {
	
	String nome;
	boolean sessioneAttiva;
	int numSessioni;
	Instant start;
	
	public Utente() {
		this.nome = "";
		this.sessioneAttiva= false;
		this.numSessioni = 1;
		this.start = Instant.now();
	}

	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public String getNome() {
		return nome;
	}

	public boolean isSessioneAttiva() {
		return sessioneAttiva;
	}

	public int getNumSessioni() {
		return numSessioni;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSessioneAttiva(boolean sessioneAttiva) {
		this.numSessioni++;
		this.sessioneAttiva = sessioneAttiva;
	}

	public void setNumSessioni(int numSessioni) {
		this.numSessioni = numSessioni;
	}
}
