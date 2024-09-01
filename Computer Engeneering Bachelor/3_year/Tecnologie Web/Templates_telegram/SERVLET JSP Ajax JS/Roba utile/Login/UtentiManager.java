package beans;

import java.util.ArrayList;
import java.util.List;

public class UtentiManager {
	
	private List<Utente> utenti;
	
	public UtentiManager () {
		utenti = new ArrayList<>();
	}
	
	public synchronized boolean addUtente(Utente utente) {
		if (utenti.size() < 50 && !utenti.contains(utente)) {
			utenti.add(utente);
			return true;
		}
		else
			return false;
	}
	
	public synchronized boolean hasUtente(Utente utente) {
		return utenti.contains(utente);
	}
	
	public synchronized int getNumUtenti() {
		return utenti.size();
	}

	public Utente getUtente(String nome, String cognome) {
		return utenti.stream.filter(u -> u.nome.equals(nome) && u.cognome.equals(cognome)).findAny().orElse(null);
	}

}
