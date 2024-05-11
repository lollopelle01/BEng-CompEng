package beans;

import java.io.Serializable;

public class Socio implements Serializable {

	private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean isAdmin;
    private int tentativi;
    private boolean logged;	
    private boolean capitano;
    private int squadra;
    
	// --- constructor ----------
	public Socio(String user, String pws) {
        this.username = user;
        this.password = pws;
        this.tentativi = 0;
        this.squadra = -1;
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
	
	public int getTentativi() {
		return tentativi;
	}
	public void setTentativi(int tentativi) {
		this.tentativi = tentativi;
	}
	
	public boolean isLogged() {
		return logged;
	}
	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public int getSquadra() {
		return squadra;
	}
	public void setSquadra(int s) {
		this.squadra = s;
	}
	
	public boolean isCapitano() {
		return capitano;
	}
	public void setCapitano(boolean b) {
		this.capitano = b;
	}

	// --- utilities ----------------------------
    public String toString() {
		return "Utente [username=" + username + ", password=" + password + "]";
	}
}
