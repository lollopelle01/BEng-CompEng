package beans;

import java.util.ArrayList;
import java.util.List;

public class AstaManager {
	
	private List<Asta> astePassate = new ArrayList<>();
	private Asta currentAsta;
	private List<Utente> utenti = new ArrayList<>();
	
	public AstaManager() {
		currentAsta = new Asta();
	}
	
	public synchronized Asta getCurrentAsta() {
		if (currentAsta.isOpen())
			return currentAsta;
		astePassate.add(currentAsta);
		currentAsta = new Asta();
		return currentAsta;
	}
	
	public synchronized Asta getLastAsta() {
		if (!currentAsta.isOpen()) {
			astePassate.add(currentAsta);
			currentAsta = new Asta();
		}
		if (astePassate.size() >= 1)
			return astePassate.get(astePassate.size()-1);
		else
			return null;
	}
	
	public synchronized boolean addUtente(Utente utente) {
		if (utenti.size() < 100 && !utenti.contains(utente)) {
			utenti.add(utente);
			return true;
		}
		else
			return false;
	}
	
	public synchronized boolean hasUtente(Utente utente) {
		return utenti.contains(utente);
	}

}
