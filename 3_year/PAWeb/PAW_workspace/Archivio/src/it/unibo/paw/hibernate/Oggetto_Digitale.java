package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.sql.Date;

public class Oggetto_Digitale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	
	private String codiceOggetto;
	private String nome;
	private String formato;
	private Date dataDigitalizzazione;
	
	private int idMateriale;
	
	// Costruttore
	public Oggetto_Digitale() {
		super();
	}
	
	// Getter e Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceOggetto() {
		return codiceOggetto;
	}

	public void setCodiceOggetto(String codiceOggetto) {
		this.codiceOggetto = codiceOggetto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Date getDataDigitalizzazione() {
		return dataDigitalizzazione;
	}

	public void setDataDigitalizzazione(Date dataDigitalizzazione) {
		this.dataDigitalizzazione = dataDigitalizzazione;
	}

	public int getIdMateriale() {
		return idMateriale;
	}

	public void setIdMateriale(int idMateriale) {
		this.idMateriale = idMateriale;
	}
}
