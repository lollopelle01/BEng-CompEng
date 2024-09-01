package it.unibo.tw.dao;

import java.io.Serializable;

public class PiattoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nomePiatto;
	private int tempoPreparazione; //in minuti
	private int calorie;
	
	
	// --- constructor ----------
	
	public PiattoDTO() {
	}
	
	public PiattoDTO(String nomePiatto, int tempoPreparazione, int calorie) {
		this.nomePiatto = nomePiatto;
		this.tempoPreparazione = tempoPreparazione;
		this.calorie = calorie;
	}
	
	
	// --- getters and setters --------------
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

	public int getTempoPreparazione() {
		return tempoPreparazione;
	}

	public void setTempoPreparazione(int tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}
	
	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
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

	
	
}
