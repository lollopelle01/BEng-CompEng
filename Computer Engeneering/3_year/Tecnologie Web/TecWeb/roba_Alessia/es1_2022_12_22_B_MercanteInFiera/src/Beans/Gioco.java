package Beans;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Gioco {

	private int numCarteInGioco; 
	private ArrayList<Utente> users;
	private LocalTime startGame;
	private Carta CartaInVendita;
	private List<Carta> carteUsate;
	
	public Gioco() {
		super();
		numCarteInGioco = 30;
		users = new ArrayList<Utente>();
		startGame = null;
		CartaInVendita = null;
		carteUsate = new ArrayList<Carta>();
		settaCarte();
	}

	public int getNumCarteInGioco() {
		return numCarteInGioco;
	}

	public void setNumCarteInGioco(int numCarteInGioco) {
		this.numCarteInGioco = numCarteInGioco;
	}

	public ArrayList<Utente> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Utente> users) {
		this.users = users;
	}

	public LocalTime getStartGame() {
		return startGame;
	}

	public void setStartGame(LocalTime startGame) {
		this.startGame = startGame;
	}

	public Carta getCartaInVendita() {
		return CartaInVendita;
	}

	public void setCartaInVendita(Carta cartaInVendita) {
		CartaInVendita = cartaInVendita;
	}

	public List<Carta> getCarteUsate() {
		return carteUsate;
	}

	public void setCarteUsate(List<Carta> carteUsate) {
		this.carteUsate = carteUsate;
	}
	
	public void settaCarte() {
		String numeri = "";
		for(int i = 0; i < 30; i++) {
			this.carteUsate.add(new Carta(i+1));
		}
	}
	
	
}
