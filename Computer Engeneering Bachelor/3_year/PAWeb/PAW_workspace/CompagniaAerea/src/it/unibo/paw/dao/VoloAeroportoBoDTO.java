package it.unibo.paw.dao;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.sql.Date;

public class VoloAeroportoBoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;
	private String codVolo;
	private String compagniaAerea;
	private String localitaDestinazione;
	private Date dataPartenza;
	private Time orarioPartenza;
	
	private ArrayList<PasseggeroDTO> passeggeri;
	
	// --- constructor ----------
	public VoloAeroportoBoDTO() {
		super();
	}

	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodVolo() {
		return codVolo;
	}

	public void setCodVolo(String codVolo) {
		this.codVolo = codVolo;
	}

	public String getCompagniaAerea() {
		return compagniaAerea;
	}

	public void setCompagniaAerea(String compagniaAerea) {
		this.compagniaAerea = compagniaAerea;
	}

	public String getLocalitaDestinazione() {
		return localitaDestinazione;
	}

	public void setLocalitaDestinazione(String localitaDestinazione) {
		this.localitaDestinazione = localitaDestinazione;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public Time getOrarioPartenza() {
		return orarioPartenza;
	}

	public void setOrarioPartenza(Time orarioPartenza) {
		this.orarioPartenza = orarioPartenza;
	}

	public ArrayList<PasseggeroDTO> getPasseggeri() {
		return passeggeri;
	}

	public void setPasseggeri(ArrayList<PasseggeroDTO> passeggeri) {
		this.passeggeri = passeggeri;
	}
}
