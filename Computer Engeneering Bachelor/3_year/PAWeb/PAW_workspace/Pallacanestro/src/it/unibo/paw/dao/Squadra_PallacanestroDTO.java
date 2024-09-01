package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class Squadra_PallacanestroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;

	private String nome;
	private String torneo;
	private String allenatore;
	
	private Set<GiocatoreDTO> giocatori;
	
	// --- constructor ----------
	public Squadra_PallacanestroDTO() {
		super();
	}
	
	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTorneo() {
		return torneo;
	}

	public void setTorneo(String torneo) {
		this.torneo = torneo;
	}

	public String getAllenatore() {
		return allenatore;
	}

	public void setAllenatore(String allenatore) {
		this.allenatore = allenatore;
	}

	public Set<GiocatoreDTO> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<GiocatoreDTO> giocatori) {
		this.giocatori = giocatori;
	}	

}
