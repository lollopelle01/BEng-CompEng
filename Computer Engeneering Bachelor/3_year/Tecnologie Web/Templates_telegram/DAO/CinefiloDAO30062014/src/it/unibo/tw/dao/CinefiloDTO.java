package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CinefiloDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nomeCinefilo;
	private String sesso;
	private int eta;
	private Set<ProiezioneDTO> proiezioni = new HashSet<>();
	
	
	
	// --- constructor ----------
	
	public CinefiloDTO() {
	}
	
	public CinefiloDTO(String nomeCinefilo, String sesso, int eta) {
		this.nomeCinefilo = nomeCinefilo;
		this.setSesso(sesso);
		this.eta = eta;
	}
	
	
	// --- getters and setters --------------
	
	public Set<ProiezioneDTO> getProiezioni() {
		return proiezioni;
	}
	
	public void setProiezioni(Set<ProiezioneDTO> proiezioni) {
		this.proiezioni = proiezioni;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNomeCinefilo() {
		return nomeCinefilo;
	}


	public void setNomeCinefilo(String nomeCinefilo) {
		this.nomeCinefilo = nomeCinefilo;
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
		CinefiloDTO other = (CinefiloDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}


}
