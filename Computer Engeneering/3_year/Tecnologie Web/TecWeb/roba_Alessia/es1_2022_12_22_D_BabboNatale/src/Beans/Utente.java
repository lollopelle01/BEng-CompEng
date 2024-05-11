package Beans;

public class Utente {

	//utente, password, denari
	private String username;
	private String password;
	private double denari;
	
	public Utente(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.denari = 100;
	}

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

	public double getDenari() {
		return denari;
	}

	public void setDenari(double denari) {
		this.denari = denari;
	}
}
