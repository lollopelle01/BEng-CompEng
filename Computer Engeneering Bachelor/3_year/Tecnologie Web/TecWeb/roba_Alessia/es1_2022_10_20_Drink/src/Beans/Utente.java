package Beans;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	
	private String username;
	private Tavolo tavolo;
	private List<Drink> ordinati;
	private List<Drink> consegnati;
	private int numTotOrd;
	private int numTotCons;
	
	public Utente(String username, Tavolo tavolo) {
		super();
		this.username = username;
		this.tavolo = tavolo;
		this.ordinati = new ArrayList<Drink>();
		this.consegnati = new ArrayList<Drink>();
		this.numTotCons = 0;
		this.numTotOrd = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	public int getNumTotOrd() {
		return numTotOrd;
	}

	public void addNumTotOrd(int numTotOrd) {
		this.numTotOrd += numTotOrd;
	}

	public int getnumTotCons() {
		return numTotCons;
	}

	public void addNumTotCons(int numTotCons) {
		this.numTotCons = numTotCons;
	}
	
	public List<Drink> getOrdinati() {
		return ordinati;
	}
	
	public void addDrinkOrd(Drink drink) {
		ordinati.add(drink);
	}
	
	public void removeDrinkOrd(Drink drink) {
		ordinati.remove(drink);
	}
	
	public void addDrinkCons(Drink drink) {
		consegnati.add(drink);
	}
	
	public List<Drink> getConsegnati() {
		return consegnati;
	}

	public double getTotCons() {
		double tot = 0.0;
		
		for(Drink drink : consegnati) {
			tot += drink.getTot();
		}
		
		return tot;
	}
	
	public double getTotOrd() {
		double tot = 0.0;
		
		for(Drink drink : ordinati) {
			tot += drink.getTot();
		}
		
		return tot;
	}
	
	
}
