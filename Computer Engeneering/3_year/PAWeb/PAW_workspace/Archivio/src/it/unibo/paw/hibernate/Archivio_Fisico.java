package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Archivio_Fisico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	
	private String codiceArchivio;
	private String nome; 
	private String descrizione;
	private Date dataCreazione;
	
	private Set<Materiale_Fisico> materiali;

	// Costruttore
	public Archivio_Fisico() {
		super();
		this.materiali = new HashSet<Materiale_Fisico>();
	}
	
	// Getter e Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceArchivio() {
		return codiceArchivio;
	}

	public void setCodiceArchivio(String codiceArchivio) {
		this.codiceArchivio = codiceArchivio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<Materiale_Fisico> getMateriali() {
		return materiali;
	}

	public void setMateriali(Set<Materiale_Fisico> materiali) {
		this.materiali = materiali;
	}

}
