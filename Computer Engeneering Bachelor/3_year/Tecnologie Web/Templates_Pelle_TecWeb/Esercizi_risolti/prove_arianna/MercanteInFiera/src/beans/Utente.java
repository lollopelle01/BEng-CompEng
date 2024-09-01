package beans;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Utente {
	
	String nome;
	boolean sessioneAttiva;
	List<Integer> carte;
	int denari;
	int cartaVendita;
	int offerta;
	Instant offertaTime;
	
	public Utente() {
		this.nome = "";
		this.sessioneAttiva= false;
		carte = new ArrayList<>();
		denari = 150;
		cartaVendita = -1;
		offerta = -1;
		offertaTime = null;
	}

	public List<Integer> getCarte() {
		return carte;
	}
	public void setCarte(List<Integer> carte) {
		this.carte = carte;
	}

	public int getDenari() {
		return denari;
	}
	public void setDenari(int denari) {
		this.denari = denari;
	}

	public int getCartaVendita() {
		return cartaVendita;
	}
	public void setCartaVendita(int cartaVendita) {
		this.cartaVendita = cartaVendita;
	}

	public int getOfferta() {
		return offerta;
	}
	public void setOfferta(int offerta) {
		this.offerta = offerta;
	}

	public Instant getOffertaTime() {
		return offertaTime;
	}
	public void setOffertaTime(Instant offertaTime) {
		this.offertaTime = offertaTime;
	}

	public void setSessioneAttiva(boolean sessioneAttiva) {
		this.sessioneAttiva = sessioneAttiva;
	}
	public boolean isSessioneAttiva() {
		return sessioneAttiva;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
