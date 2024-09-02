package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Materiale_Fisico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	
	private String codiceMateriale;
	private String nome;
	private String descrizione;
	private Date dataCreazione;
	
	private int idArchivio;
	
	private Set<Oggetto_Digitale> oggetti;

	// Costruttore
	public Materiale_Fisico() {
		super();
		oggetti = new HashSet<Oggetto_Digitale>();
	}

	// Getter e Setter
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCodiceMateriale() {
		return codiceMateriale;
	}


	public void setCodiceMateriale(String codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
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

	public Set<Oggetto_Digitale> getOggetti() {
		return oggetti;
	}

	public void setOggetti(Set<Oggetto_Digitale> oggetti) {
		this.oggetti = oggetti;
	}

	public int getIdArchivio() {
		return idArchivio;
	}

	public void setIdArchivio(int idArchivio) {
		this.idArchivio = idArchivio;
	}
	
}
