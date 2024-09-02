package Beans;

public class Offerta {

	private String utente;
	private double offerta;
	
	public Offerta(String nome, double denari) {
		super();
		this.utente = nome;
		this.offerta = denari;
	}
	
	public Offerta() {
		super();
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public double getOfferta() {
		return offerta;
	}

	public void setOfferta(double offerta) {
		this.offerta = offerta;
	}
	
	
}
