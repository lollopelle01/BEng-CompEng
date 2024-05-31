package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.sql.Date;

public class PartitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;

	private String codicePartita;
	private String categoria;
	private String girone;
	private String nomeSquadraCasa;
	private String nomeSquadraOspite;
	private Date data;

	private int idStadio;
	
	// --- constructor ----------
	public PartitaDTO() {
		super();
	}

	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodicePartita() {
		return codicePartita;
	}

	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getGirone() {
		return girone;
	}

	public void setGirone(String girone) {
		this.girone = girone;
	}

	public String getNomeSquadraCasa() {
		return nomeSquadraCasa;
	}

	public void setNomeSquadraCasa(String nomeSquadraCasa) {
		this.nomeSquadraCasa = nomeSquadraCasa;
	}

	public String getNomeSquadraOspite() {
		return nomeSquadraOspite;
	}

	public void setNomeSquadraOspite(String nomeSquadraOspite) {
		this.nomeSquadraOspite = nomeSquadraOspite;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getIdStadio() {
		return idStadio;
	}

	public void setIdStadio(int idStadio) {
		this.idStadio = idStadio;
	}

}
