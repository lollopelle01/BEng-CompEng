package Beans;

import java.util.Random;

public class Tennista {

	private String cognome;
	private int ranking, win, lose, titoli, tab;

	public Tennista(String cognome, int ranking, int win, int lose, int titoli, int tab) {
		super();
		this.cognome = cognome;
		this.ranking = ranking;
		this.win = win;
		this.lose = lose;
		this.titoli = titoli;
		this.tab = tab;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getTitoli() {
		return titoli;
	}

	public void setTitoli(int titoli) {
		this.titoli = titoli;
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}
	
	
	
	
	
}
