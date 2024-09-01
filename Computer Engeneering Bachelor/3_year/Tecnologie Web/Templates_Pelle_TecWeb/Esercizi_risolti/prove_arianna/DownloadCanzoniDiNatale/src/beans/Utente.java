package beans;

import java.io.Serializable;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean isAdmin;
    private boolean logged;
	
    
	// --- constructor ----------
	public Utente(String user, String pws) {
        this.username = user;
        this.password = pws;
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
	
	public boolean isLogged() {
		return logged;
	}
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	

	// --- utilities ----------------------------
    public String toString() {
		return "Utente [username=" + username + ", password=" + password + "]";
	}
}
