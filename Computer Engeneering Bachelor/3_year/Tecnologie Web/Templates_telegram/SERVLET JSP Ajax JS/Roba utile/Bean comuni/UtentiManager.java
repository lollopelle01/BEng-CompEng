package beans;

import java.util.ArrayList;
import java.util.List;

public class UtentiManager {
	
	private List<Utente> utenti;
	
	public UtentiManager () {
		utenti = new ArrayList<>();
	}
	
	private synchronized boolean addUtente(Utente utente) {
		if (utenti.size() < 50 && !utenti.contains(utente)) {
			utenti.add(utente);
			return true;
		}
		else
			return false;
	}
	
	public Utente getUtente(String nome, String cognome, Catalogo catalogo) {
		for (Utente u : utenti) {
			if (u.getNome().equals(nome) && u.getCognome().equals(cognome)) {
				u.popolaSessioneCorrente(catalogo);
				return u;
			}
		}
		Utente u = new Utente(nome, cognome);
		u.popolaSessioneCorrente(catalogo);
		addUtente(u);
		return u;
	}
	
	public synchronized boolean hasUtente(Utente utente) {
		return utenti.contains(utente);
	}
	
	public synchronized int getNumUtenti() {
		return utenti.size();
	}

}
