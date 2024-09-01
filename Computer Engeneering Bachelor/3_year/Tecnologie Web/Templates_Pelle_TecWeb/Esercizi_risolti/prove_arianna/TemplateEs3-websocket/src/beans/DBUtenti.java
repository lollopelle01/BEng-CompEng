package beans;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DBUtenti {
	
	List<Utente> utenti;
	Instant start;
	
	public DBUtenti() {
		this.utenti = new ArrayList<>();
		this.start = Instant.now();
	}

	public Utente findUtente(String name) {
		for (Utente item : utenti) {
			if(item.getNome().equals(name))
				return item;
		}
		return null;
	}
	
	public void addUtente(Utente item) {
		utenti.add(item);
	}
	
	public void rimuoviUtente(String name) {
		for (Utente item : utenti) {
			if(item.getNome().equals(name))
				utenti.remove(item);
		}
	}
	
	public void terminaSessione(String name) {
		for (Utente item : utenti) {
			if(item.getNome().equals(name))
				item.setSessioneAttiva(false);
		}
	}
	
	public void terminaSessioneTutti() {
		for (Utente item : utenti) {
			item.setSessioneAttiva(false);
		}
	}
	
	public void setStart() {
		start = Instant.now();
	}
	
	public List<Utente> getUtentiAttivi(){
		List<Utente> result = new ArrayList<>();
		for (Utente item : utenti) {
			if(item.isSessioneAttiva())
				result.add(item);
		}
		return result;
	}
	
	public List<Utente> getUtentiAttiviUltimi(int giorni){
		List<Utente> result = new ArrayList<>();
		for (Utente item : utenti) {
			if(Duration.between(item.getStart(), Instant.now()).toDays()<=giorni)
				result.add(item);
		}
		return result;
	}
	
	
}

