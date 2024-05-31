package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Set;

public class GiocatoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;
	
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private int eta;
	
	private Set<Squadra_PallacanestroDTO> squadre;

	// --- constructor ----------
	public GiocatoreDTO() {
		super();
	}

	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public Set<Squadra_PallacanestroDTO> getSquadre() {
		return squadre;
	}

	public void setSquadre(Set<Squadra_PallacanestroDTO> squadre) {
		this.squadre = squadre;
	}		

}
