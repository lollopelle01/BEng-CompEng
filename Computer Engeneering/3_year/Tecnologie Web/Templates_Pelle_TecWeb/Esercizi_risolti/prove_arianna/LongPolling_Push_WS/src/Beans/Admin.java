package Beans;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;

import javax.websocket.Session;

public class Admin {

	/* visualizzare tutti gli utenti correntemente iscritti
	 * al servizio, con associato il timestamp di loro iscrizione,
	 * e ordinati in ordine di durata decrescente dell’iscrizione */
	
	private List<Entry<Session, LocalDateTime>> usersOrdinati;
	private String action;

	public Admin(String action, List<Entry<Session, LocalDateTime>> utentiOrd) {
		super();
		this.action = action;
		this.usersOrdinati = utentiOrd;
	}

	public List<Entry<Session, LocalDateTime>> getUsersOrdinati() {
		return usersOrdinati;
	}

	public void setUsersOrdinati(List<Entry<Session, LocalDateTime>>usersOrdinati) {
		this.usersOrdinati = usersOrdinati;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
