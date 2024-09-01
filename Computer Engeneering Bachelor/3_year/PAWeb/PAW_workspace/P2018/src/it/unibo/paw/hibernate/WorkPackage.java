package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WorkPackage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	private String nomeWP;
	private String titolo;
	private String descrizione;
	
	private Progetto progetto;
	private Set<Partner> partners;

	// Costruttore
	public WorkPackage() {
		super();
		this.partners = new HashSet<Partner>();
	}

	// Getter e Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeWP() {
		return nomeWP;
	}

	public void setNomeWP(String nomeWP) {
		this.nomeWP = nomeWP;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Progetto getProgetto() {
		return progetto;
	}

	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}

	public Set<Partner> getPartners() {
		return partners;
	}

	public void setPartners(Set<Partner> partners) {
		this.partners = partners;
	}
	

}
