package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

public class VoloAeroportoBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	private String codVolo;
	private String compagniaAerea;
	private String localitaDestinazione;
	private Date dataPartenza;
	private Time orarioPartenza;
	
	private Set<Passeggero> passeggeri;

	// Costruttore
	public VoloAeroportoBO() {
		super();
	}

	// Getter e Setterpasseggeri
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

	public Set<Passeggero> getPasseggeri() {
		return passeggeri;
	}

	public void setPasseggeri(Set<Passeggero> passeggeri) {
		this.passeggeri = passeggeri;
	}	

}
