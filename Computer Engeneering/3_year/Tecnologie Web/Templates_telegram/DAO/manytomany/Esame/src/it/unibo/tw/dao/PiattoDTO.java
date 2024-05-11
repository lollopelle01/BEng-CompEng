package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PiattoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nomePiatto;
	private String tipo;
	private Set<RistoranteDTO> ristoranti = new HashSet<>();
	
	
	
	// --- constructor ----------
	
	public PiattoDTO() {
	}
	
	public PiattoDTO(String nomePiatto, String tipo) {
		this.nomePiatto = nomePiatto;
		this.setTipo(tipo);
	}
	
	
	// --- getters and setters --------------
	
	public Set<RistoranteDTO> getRistoranti() {
		return ristoranti;
	}
	
	public void setRistoranti(Set<RistoranteDTO> ristoranti) {
		this.ristoranti = ristoranti;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNomePiatto() {
		return nomePiatto;
	}


	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	

	// --- utilities ----------------------------
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PiattoDTO other = (PiattoDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
