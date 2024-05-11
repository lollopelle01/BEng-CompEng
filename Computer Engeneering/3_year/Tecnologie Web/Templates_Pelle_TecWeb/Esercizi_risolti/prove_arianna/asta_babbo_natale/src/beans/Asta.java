package beans;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Asta implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Regalo> regaliDavendere;
	private Regalo inVendita;
	private List<Offerta> venduti;
	
	private List<Utente> utenti;    //utenti autenticati che possono partecipare all'asta
	private Instant startTime;
	private boolean start;
	
	//constructor
	public Asta() {
		this.regaliDavendere = new ArrayList<Regalo>();
		regaliDavendere.add(new Regalo("cuffie", "beats", 25));
		//settaggio dei vari regali...10 in totale
		
		this.utenti = new ArrayList<Utente>();
		this.venduti = new ArrayList<Offerta>();
		start = false;
	}

	//get e set
	public List<Utente> getUtenti() {
		return utenti;
	}
	public void addUtente(Utente u) {
		this.utenti.add(u);
	}
	
	public boolean isStart() {
		return this.start;
	}
	public void setStart(boolean b) {
		this.start = b;
	}
	
	public List<Regalo> getRegaliDavendere() {
		return this.regaliDavendere;
	}
	
	public List<Offerta> getVenduti() {
		return this.venduti;
	}
	
	public void setInvendita(Regalo r) {
		this.inVendita = r;
	}
	public Regalo getInvendita() {
		return this.inVendita;
	}
	
	//utilities
	public void init() {
		this.startTime = Instant.now(); //iniziano le 2 ore di asta
		this.setStart(true);
	}
	
	public boolean isTimeout() {
		if(Duration.between(startTime, Instant.now()).toHours() >= 2)
			return true;
		else
			return false;
	}
}
