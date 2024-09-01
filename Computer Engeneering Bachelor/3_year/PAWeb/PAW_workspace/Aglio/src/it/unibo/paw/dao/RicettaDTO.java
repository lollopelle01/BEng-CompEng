package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class RicettaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;
	private String nomeRicetta;
	private int tempoPreparazione;
	private int livelloDifficolta;
	private int calorie;
	
	// --- constructor ----------
	public RicettaDTO() {
		super();
	}
	
	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeRicetta() {
		return nomeRicetta;
	}

	public void setNomeRicetta(String nomeRicetta) {
		this.nomeRicetta = nomeRicetta;
	}

	public int getTempoPreparazione() {
		return tempoPreparazione;
	}

	public void setTempoPreparazione(int tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}

	public int getLivelloDifficolta() {
		return livelloDifficolta;
	}

	public void setLivelloDifficolta(int livelloDifficolta) {
		this.livelloDifficolta = livelloDifficolta;
	}

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
}
