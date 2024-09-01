package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class IngredienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;
	private String nomeIngrediente;
	private int quantita;
	private ArrayList<RicettaDTO> ricette;
	
	// Per lazy-load
	private boolean alreadyLoaded;
	
	// --- constructor ----------
	public IngredienteDTO() {
		super();
	}
	
	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeIngrediente() {
		return nomeIngrediente;
	}

	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public ArrayList<RicettaDTO> getRicette() {
		return ricette;
	}

	public void setRicette(ArrayList<RicettaDTO> ricette) {
		this.ricette = ricette;
	}

	public boolean isAlreadyLoaded() {
		return alreadyLoaded;
	}

	public void setAlreadyLoaded(boolean alreadyLoaded) {
		this.alreadyLoaded = alreadyLoaded;
	}
	
	
}
