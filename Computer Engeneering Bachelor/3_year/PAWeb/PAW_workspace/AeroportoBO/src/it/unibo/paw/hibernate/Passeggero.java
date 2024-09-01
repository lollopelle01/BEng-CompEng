package it.unibo.paw.hibernate;

import java.io.Serializable;
import java.util.Set;

public class Passeggero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Attributi
	private int id;
	private String codPasseggero;
	private String nome;
	private String cognome;
	private int numPassaporto;
	
	private Set<VoloAeroportoBO> voliAeroportoBO;

	// Costruttore
	public Passeggero() {
		super();
	}

	// Getter e Setter
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

	public Set<VoloAeroportoBO> getVoliAeroportoBO() {
		return voliAeroportoBO;
	}

	public void setVoliAeroportoBO(Set<VoloAeroportoBO> voliAeroportoBO) {
		this.voliAeroportoBO = voliAeroportoBO;
	}	

}
