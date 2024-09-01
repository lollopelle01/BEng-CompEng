package Beans;

import java.util.ArrayList;
import java.util.List;

public class Regalo {

	private String nome;		//nome regalo
	private String descr;		//descrizione
	private String continente;	//continente in cui può essere consegnato
	private double prezzo; 		//prezzo base d'asta
	private boolean acquistato;
	private List<Offerta> offerte;
	private Offerta offertaVinc;
	
	public Regalo(String nome, String descr, String continente, double prezzo) {
		super();
		this.nome = nome;
		this.descr = descr;
		this.continente = continente;
		this.prezzo = prezzo;
		this.acquistato = false;
		this.offerte = new ArrayList<>();
		this.offertaVinc = new Offerta();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getContinente() {
		return continente;
	}

	public void setContinente(String continente) {
		this.continente = continente;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isAcquistato() {
		return acquistato;
	}

	public void setAcquistato(boolean acquistato) {
		this.acquistato = acquistato;
	}

	public Offerta getOffertaVinc() {
		return offertaVinc;
	}

	public void setOffertaVinc(Offerta offerta) {
		this.offertaVinc = offerta;
	}

	public List<Offerta> getOfferte() {
		return offerte;
	}

	public void setOfferte(List<Offerta> offerte) {
		this.offerte = offerte;
	}

	
	
	
}
