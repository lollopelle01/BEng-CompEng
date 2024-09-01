package it.unibo.paw.model;

import java.io.Serializable;

public class Acquisto implements Serializable{
	private int id;
	private String codiceAcquisto;
	private double importo;
	private String nomeAcquirente;
	private String CognomeAcquirente;
	
	public Acquisto() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceAcquisto() {
		return codiceAcquisto;
	}

	public void setCodiceAcquisto(String codiceAcquisto) {
		this.codiceAcquisto = codiceAcquisto;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public String getNomeAcquirente() {
		return nomeAcquirente;
	}

	public void setNomeAcquirente(String nomeAcquirente) {
		this.nomeAcquirente = nomeAcquirente;
	}

	public String getCognomeAcquirente() {
		return CognomeAcquirente;
	}

	public void setCognomeAcquirente(String cognomeAcquirente) {
		CognomeAcquirente = cognomeAcquirente;
	}
	
	
	
}
