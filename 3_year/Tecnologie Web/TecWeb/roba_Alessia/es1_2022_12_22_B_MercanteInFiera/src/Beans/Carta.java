package Beans;

public class Carta {

	private Utente utente;
	private int numero;
	
	public Carta(int num) {
		super();
		this.utente = null;
		this.numero = num;
	}
	
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
}
