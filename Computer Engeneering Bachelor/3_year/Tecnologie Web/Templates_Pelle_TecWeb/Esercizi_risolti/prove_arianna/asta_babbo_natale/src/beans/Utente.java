package beans;

import java.io.Serializable;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean isAdmin;
    private int denari;
    private boolean logged;
    //private LocalDateTime ultimaModifica;
	
    
	// --- constructor ----------
	public Utente(String user, String pws) {
        this.username = user;
        this.password = pws;
        this.denari = 100;
	}

	// --- getters and setters --------------
	public String getUsername() {
        return this.username;
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
    
    public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public int getDenari() {
		return denari;
	}
	public void setDenari(int d) {
		this.denari = d;
	}
	
	public boolean isLogged() {
		return logged;
	}
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
	
	/*public String getEmail() {
        return email;
    }
    public void setEmail (String email) {
        this.email = email;
    }*/

	/*public LocalDateTime getUltimaModifica() {
		return ultimaModifica;
	} 
	public void setUltimaModifica(LocalDateTime ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}*/

	/*public int getGruppo() {
		return gruppo;
	}
	public void setGruppo(int gruppo) {
		this.gruppo = gruppo;
	}*/


	// --- utilities ----------------------------
    public String toString() {
		return "Utente [username=" + username + ", password=" + password + "]";
	}
}
