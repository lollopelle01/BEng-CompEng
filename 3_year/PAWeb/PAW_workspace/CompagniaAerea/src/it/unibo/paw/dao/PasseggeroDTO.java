package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class PasseggeroDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;
	private String codPasseggero;
	private String nome;
	private String cognome;
	private int numPassaporto;
	
	private ArrayList<VoloAeroportoBoDTO> voli;
	
	// --- constructor ----------
	public PasseggeroDTO() {
		super();
	}
	
	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodPasseggero() {
		return codPasseggero;
	}

	public void setCodPasseggero(String codPasseggero) {
		this.codPasseggero = codPasseggero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getNumPassaporto() {
		return numPassaporto;
	}

	public void setNumPassaporto(int numPassaporto) {
		this.numPassaporto = numPassaporto;
	}

	public ArrayList<VoloAeroportoBoDTO> getVoli() {
		return voli;
	}

	public void setVoli(ArrayList<VoloAeroportoBoDTO> voli) {
		this.voli = voli;
	}	

}
