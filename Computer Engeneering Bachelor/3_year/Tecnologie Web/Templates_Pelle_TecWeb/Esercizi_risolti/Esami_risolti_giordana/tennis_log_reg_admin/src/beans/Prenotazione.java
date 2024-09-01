package beans;

import java.time.LocalDateTime;

public class Prenotazione {

	private String username1;
	private String username2;
	private int giorno;
	private int orario;
	private boolean occupato;
	private LocalDateTime start;
	
	public Prenotazione(String username1, int giorno, int orario) {
		this.username1=username1;
		this.giorno=giorno;
		this.orario=orario;
		this.username2="";
		this.occupato = false;
		this.start = null;
	}

	public String getUsername1() {
		return username1;
	}

	public void setUsername1(String username1) {
		this.username1 = username1;
	}

	public String getUsername2() {
		return username2;
	}

	public void setUsername2(String username2) {
		this.username2 = username2;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public int getOrario() {
		return orario;
	}

	public void setOrario(int orario) {
		this.orario = orario;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	
	
}
