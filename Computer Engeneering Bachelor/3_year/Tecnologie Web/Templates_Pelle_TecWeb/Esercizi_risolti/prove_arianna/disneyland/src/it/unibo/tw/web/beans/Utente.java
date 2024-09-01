package it.unibo.tw.web.beans;

import javax.servlet.http.HttpSession;

public class Utente {

    //valiabili globali
	String username;
	String password;
	String gruppo;
	private HttpSession session;
	
	
	// --- constructor ----------
	public Utente(String ut, String psw) {
		this.username = ut;
		this.password = psw;
	}

	// --- getters and setters --------------
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGruppo() {
		return this.gruppo;
	}
	
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}
	
	public HttpSession getSession() {
		return this.session;
	};
	
	public void setSession(HttpSession s) {
		this.session = s;
	};

	// --- utilities ----------------------------
	@Override
	public boolean equals(Object obj) {
		
		Utente u = (Utente)obj;
        //usando la compareTo compara tutte le variabili es.
		if(u.getUsername().compareTo(u.username)==0 && u.getPassword().compareTo(u.password)==0)
			return true;
		else
			return false;
	}

    public String toString() {
		return "Utente [username=" + this.username + ", password=" + this.password + "]";
	}
	
}
