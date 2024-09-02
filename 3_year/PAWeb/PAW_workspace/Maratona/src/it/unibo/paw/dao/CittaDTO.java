package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class CittaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	private int id;
	
	private String nome;
	private String nazione;

	// Per lazy-load
	private boolean alreadyLoaded;
	
	private Set<MaratonaDTO> maratone;
	
	// --- constructor ----------
	public CittaDTO() {
		super();
	}

	// --- getters and setters --------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public boolean isAlreadyLoaded() {
		return alreadyLoaded;
	}

	public void setAlreadyLoaded(boolean alreadyLoaded) {
		this.alreadyLoaded = alreadyLoaded;
	}

	public Set<MaratonaDTO> getMaratone() {
		return maratone;
	}

	public void setMaratone(Set<MaratonaDTO> maratone) {
		this.maratone = maratone;
	}
}
