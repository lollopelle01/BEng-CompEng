package beans;

import java.util.List;

public class Giocatore {

	private int denari;
	private List<Integer> carte;
	private String username;
	
	public Giocatore(String Username, List<Integer> carte) {
		this.username= Username;
		this.carte=carte;
		this.denari = 150;
	}

	public int getDenari() {
		return denari;
	}

	public void setDenari(int denari) {
		this.denari = denari;
	}

	

	public List<Integer> getCarte() {
		return carte;
	}

	public void setCarte(List<Integer> carte) {
		this.carte = carte;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int findPosition(int carta) {
		return this.carte.indexOf(carta);
	}
	
	public String toString() {
		return "Il giocatore"+this.username+", si è unito alla partita e gli sono state assegnate le carte: " + this.carte.toString();
	}
}
