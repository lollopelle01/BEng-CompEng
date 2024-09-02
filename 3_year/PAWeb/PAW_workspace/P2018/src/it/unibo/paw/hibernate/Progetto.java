package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Progetto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	private String codPorgetto;
	private String nomeProgetto;
	private int annoInizio;
	private int durata;
	
	private Set<WorkPackage> workPackages;

	// Costruttore
	public Progetto() {
		super();
		this.workPackages = new HashSet<WorkPackage>();
	}
	
	// Getter e Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodPorgetto() {
		return codPorgetto;
	}

	public void setCodPorgetto(String codPorgetto) {
		this.codPorgetto = codPorgetto;
	}

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}

	public int getAnnoInizio() {
		return annoInizio;
	}

	public void setAnnoInizio(int annoInizio) {
		this.annoInizio = annoInizio;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public Set<WorkPackage> getWorkPackages() {
		return workPackages;
	}

	public void setWorkPackages(Set<WorkPackage> workPackages) {
		this.workPackages = workPackages;
	}
	
	

}
