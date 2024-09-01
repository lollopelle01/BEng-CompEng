package Beans;

import java.util.ArrayList;
import java.util.List;

public class Asta {

	private long startTime;
	private List<Utente> users;		//utenti che ci partecipano
	private List<Regalo> regali;	//regali possibili con boolean che dice se è stato preso o meno
	private Regalo currentRegalo;
	private Utente currentUtente;
	private List<Regalo> regaliVenduti;
	private List<Offerta> offerte;
	
	public Asta() {
		super();
		this.users = new ArrayList<>();
		this.regali = new ArrayList<>();
		this.regaliVenduti = new ArrayList<>();
		this.offerte = new ArrayList<>();
		this.startTime = -1;
	}
	
	public List<Utente> getUsers() {
		return users;
	}
	
	public void addUsers(Utente user) {
		users.add(user);
	}
	
	public List<Regalo> getRegali() {
		return regali;
	}
	
	public void addRegalo(Regalo regalo) {
		regali.add(regalo);
	}

	public Regalo getCurrentRegalo() {
		return currentRegalo;
	}

	public void setCurrentRegalo(Regalo currentRegalo) {
		this.currentRegalo = currentRegalo;
	}

	public Utente getUtenteRegalo() {
		return currentUtente;
	}
	
	public void setOfferente(Utente utente) {
		this.currentUtente = utente;
	}

	public List<Regalo> getRegaliVenduti() {
		return regaliVenduti;
	}

	public void addRegaloVenduto(Regalo regalo) {
		this.regaliVenduti.add(regalo);
	}

	public List<Offerta> getOfferte() {
		return offerte;
	}

	public void addOfferta(Offerta offerta) {
		this.offerte.add(offerta);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
}
