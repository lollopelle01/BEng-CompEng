package beans;

import java.util.ArrayList;
import java.util.List;

public class Tabellone {
	private List<Player> players;
	
	public Tabellone() {
		this.players = new ArrayList<>();
		
		for(int i = 0; i <16; i++) {
			this.players.add(new Player("Micello", 2, 5, 100, 76));
			this.players.add(new Player("Buono", 1, 30, 150, 1));
			this.players.add(new Player("Costa", 100, 0, 3, 150));
			this.players.add(new Player("Martucci", 50, 2, 10, 190));
		}
		for(int i = 0; i <16; i++) {
			this.players.add(new Player("Bellinelli", 10, 5, 100, 76));
			this.players.add(new Player("Ibrahimovic", 20, 30, 150, 1));
			this.players.add(new Player("Che Guevara", 23, 0, 3, 150));
			this.players.add(new Player("Berlusconi", 3, 2, 10, 190));
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Player getPlayer(String surname) {
		for(Player p : players) {
			if(p.getSurname().equals(surname)) {
				return p;
			}
		}
		
		return null;
	}
}
