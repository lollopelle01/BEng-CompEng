package beans;

import java.util.ArrayList;
import java.util.List;

public class Acquirente {

	private int denari;
	private String username;
	private List<Regalo> regali;
	
	public Acquirente(String username) {
		this.denari=100;
		this.username=username;
		this.regali = new ArrayList<>();
	}

	public int getDenari() {
		return denari;
	}

	public void setDenari(int denari) {
		this.denari = denari;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Regalo> getRegali() {
		return regali;
	}

	public void setRegali(List<Regalo> regali) {
		this.regali = regali;
	}
	
	public void addRegalo(Regalo r) {
		this.regali.add(r);
	}
}
