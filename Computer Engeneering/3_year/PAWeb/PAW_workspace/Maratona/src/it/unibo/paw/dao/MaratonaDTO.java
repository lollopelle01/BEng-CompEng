package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.sql.Date;

public class MaratonaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	private int id;
	
	private String codiceMaratona;
	private String titolo;
	private Date data;
	private String tipo;
	
	private CittaDTO citta;

	// Per lazy-load
	private boolean alreadyLoaded;
	
	// --- constructor ----------
	public MaratonaDTO() {
		super();
	}

	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceMaratona() {
		return codiceMaratona;
	}

	public void setCodiceMaratona(String codiceMaratona) {
		this.codiceMaratona = codiceMaratona;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CittaDTO getCitta() {
		return citta;
	}

	public void setCitta(CittaDTO citta) {
		this.citta = citta;
	}

	public boolean isAlreadyLoaded() {
		return alreadyLoaded;
	}

	public void setAlreadyLoaded(boolean alreadyLoaded) {
		this.alreadyLoaded = alreadyLoaded;
	}
}
