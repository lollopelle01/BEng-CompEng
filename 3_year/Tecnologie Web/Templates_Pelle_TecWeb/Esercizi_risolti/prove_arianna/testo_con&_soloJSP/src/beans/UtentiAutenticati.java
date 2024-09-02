package beans;

import java.util.ArrayList;
import java.util.List;

public class UtentiAutenticati {

    private List<Utente> utenti;

    // --- constructor ----------
    public UtentiAutenticati() {
		this.utenti = new ArrayList<Utente>();
		
		Utente u = new Utente("admin","admin");
    	this.utenti.add(u);
    	//in caso ne aggiungo altri qui
	}
	
	// --- getters and setters --------------
    public List<Utente> getUtenti() {
		return utenti;
	}

	// --- utilities ----------------------------
    public void addUtente(String username, String password) {
		this.utenti.add(new Utente(username, password));
	}
    
    public Utente findUtente(String username, String password) {
		for(Utente u : getUtenti()) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password))
				return u;
		}
		return null;
	}
    
    public boolean isAutenticato(String username, String password) {
		if(this.findUtente(username, password)==null) 
			return false;
		else 
			return true;
	}
    
    public List<String> getUsernames(){
		List<String> res=new ArrayList<String>();
		for(Utente u:this.getUtenti()) {
			res.add(u.getUsername());
		}
		return res;
	}
	
	public void removeUtente(Utente u) {
		this.utenti.remove(u);
	}
	
	public Utente getUtenteByName(String username) {
		if(username.isEmpty() || username == null)
			return null;
		for(Utente u : this.utenti) {
			if(u.getUsername().compareTo(username)==0)
				return u;
		}
		return null;
	}
}
