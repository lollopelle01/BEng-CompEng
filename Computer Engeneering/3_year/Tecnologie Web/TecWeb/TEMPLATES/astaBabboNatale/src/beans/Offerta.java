package beans;

public class Offerta {

	private String username;
	private int offerta;
	
	public Offerta(int offerta, String username) {
		this.offerta=offerta;
		this.username=username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOfferta() {
		return offerta;
	}

	public void setOfferta(int offerta) {
		this.offerta = offerta;
	}
	
	
}
