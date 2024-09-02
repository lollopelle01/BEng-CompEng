package it.unibo.paw.model;

import java.io.Serializable;

public class ProdottoOfferto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int codiceProdotto;
	private String descrizione;
	private String marca;
	private Float prezzo;
	
	private int idSupermercato;

	public ProdottoOfferto() {
		super();
	}

	public int getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Float prezzo) {
		this.prezzo = prezzo;
	}

	public int getIdSupermercato() {
		return idSupermercato;
	}

	public void setIdSupermercato(int idSupermercato) {
		this.idSupermercato = idSupermercato;
	}
	
	
}
