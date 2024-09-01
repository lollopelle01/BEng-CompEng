package beans;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8255083448882764556L;
	
	private String surname;
	private int ranking, titles, win, lost;
	
	public Player(String surname, int ranking, int titles, int win, int lost) {
		this.surname = surname;
		this.ranking = ranking;
		this.titles = titles;
		this.win = win;
		this.lost = lost;
	}
	
	public Player() {
		super();
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getTitles() {
		return titles;
	}
	public void setTitles(int titles) {
		this.titles = titles;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLost() {
		return lost;
	}
	public void setLost(int lost) {
		this.lost = lost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "Player [surname=" + surname + ", ranking=" + ranking + ", titles=" + titles + ", win=" + win + ", lost="
				+ lost + "]";
	}
}
