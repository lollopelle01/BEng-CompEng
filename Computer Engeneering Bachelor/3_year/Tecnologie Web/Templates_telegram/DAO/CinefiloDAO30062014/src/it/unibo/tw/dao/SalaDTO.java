package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SalaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String nome;
	private int capienza;
	private Set<ProiezioneDTO> proiezioni = new HashSet<>();
	
	


	public String getNome() {
		return nome;
	}






	public SalaDTO() {
		super();
	}






	public SalaDTO(String nome, int capienza) {
		super();
		this.nome = nome;
		this.capienza = capienza;
	}






	public void setNome(String nome) {
		this.nome = nome;
	}






	public int getCapienza() {
		return capienza;
	}






	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}






	public Set<ProiezioneDTO> getProiezioni() {
		return proiezioni;
	}






	public void setProiezioni(Set<ProiezioneDTO> proiezioni) {
		this.proiezioni = proiezioni;
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
		SalaDTO other = (SalaDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}






	public int getId() {
		return id;
	}






	public void setId(int id) {
		this.id = id;
	}


}
