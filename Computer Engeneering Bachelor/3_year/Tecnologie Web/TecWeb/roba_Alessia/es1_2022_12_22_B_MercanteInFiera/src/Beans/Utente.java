package Beans;

import java.util.ArrayList;
import java.util.List;


public class Utente {

	//utente, password, denari
	private String username;
	private String password;
	private int denari;
	private List<Carta> carte;
	
	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		denari = 150;
		carte = new ArrayList<Carta>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDenari() {
		return denari;
	}

	public void setDenari(int denari) {
		this.denari = denari;
	}

	public List<Carta> getCarte() {
		return carte;
	}

	public void setCarte(List<Carta> carte) {
		this.carte = carte;
	}
	
	public void addCarta(Carta carta) {
		this.carte.add(carta);
	}
}
