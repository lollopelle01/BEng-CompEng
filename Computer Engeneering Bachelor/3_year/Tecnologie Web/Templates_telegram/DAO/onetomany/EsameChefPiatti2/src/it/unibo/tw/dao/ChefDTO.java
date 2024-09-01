package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ChefDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nomeChef;
	private int numeroStelle;
	private String nomeRistorante;
	private Set<PiattoDTO> piatti = new HashSet<>();
	
	
	public ChefDTO() {
		
	}
	
	public ChefDTO(String nomeChef, int numeroStelle, String nomeRistorante) {
		super();
		this.nomeChef = nomeChef;
		this.numeroStelle = numeroStelle;
		this.nomeRistorante = nomeRistorante;
	}



	// --- utilities ----------------------------
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNomeChef() {
		return nomeChef;
	}



	public void setNomeChef(String nomeChef) {
		this.nomeChef = nomeChef;
	}



	public int getNumeroStelle() {
		return numeroStelle;
	}



	public void setNumeroStelle(int numeroStelle) {
		this.numeroStelle = numeroStelle;
	}



	public String getNomeRistorante() {
		return nomeRistorante;
	}



	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}



	public Set<PiattoDTO> getPiatti() {
		return piatti;
	}



	public void setPiatti(Set<PiattoDTO> piatti) {
		this.piatti = piatti;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChefDTO other = (ChefDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
