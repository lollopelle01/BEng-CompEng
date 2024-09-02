package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RistoranteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nomeRistorante;
	private String indirizzo;
	private int rating;
	private Set<PiattoDTO> piatti = new HashSet<>();
	
	
	
	// --- constructor ----------
	
	public RistoranteDTO() {
	}
	
	public RistoranteDTO(String nomeRistorante, String indirizzo, int rating) throws IllegalArgumentException {
		this.nomeRistorante = nomeRistorante;
		this.indirizzo = indirizzo;
		if (rating > 5 || rating < 1)
			throw new IllegalArgumentException("Rating non valido");
		this.rating = rating;
	}
	
	
	// --- getters and setters --------------
	
	public Set<PiattoDTO> getPiatti() {
		return piatti;
	}
	
	public void setPiatti(Set<PiattoDTO> piatti) {
		this.piatti = piatti;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNomeRistorante() {
		return nomeRistorante;
	}


	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		if (rating > 5 || rating < 1)
			throw new IllegalArgumentException("Rating non valido");
		this.rating = rating;
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
		RistoranteDTO other = (RistoranteDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
