package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class StadioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int codice;
	private String nome;
	private String citta;
	private Set<PartitaDTO> partite = new HashSet<>();
	
	
	
	// --- constructor ----------


	public StadioDTO() {
	}
	
	public StadioDTO(String nome, String citta) throws IllegalArgumentException {
		this.nome = nome;
		this.citta = citta;
	}
	
	
	// --- getters and setters --------------
	
	
	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Set<PartitaDTO> getPartite () {
		return partite;
	}

	public void setPartite(Set<PartitaDTO> partite) {
		this.partite = partite;
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
		StadioDTO other = (StadioDTO) obj;
		if (codice != other.codice)
			return false;
		return true;
	}


}
