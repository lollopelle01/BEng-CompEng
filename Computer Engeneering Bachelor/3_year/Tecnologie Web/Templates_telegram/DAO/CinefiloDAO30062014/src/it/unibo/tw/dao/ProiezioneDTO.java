package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ProiezioneDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int id;
	private String titolo;
	private String regista;
	private String genere;
	private Date data;
	private Set<CinefiloDTO> cinefili = new HashSet<>();
	private SalaDTO sala;
	
	
	
	// --- constructor ----------
	
	public ProiezioneDTO(String titolo, String regista, String genere, Date data) {
		super();
		this.titolo = titolo;
		this.regista = regista;
		this.genere = genere;
		this.data = data;
	}



	public ProiezioneDTO() {
	}
	
	

	// --- utilities ----------------------------
	



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTitolo() {
		return titolo;
	}



	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}



	public String getRegista() {
		return regista;
	}



	public void setRegista(String regista) {
		this.regista = regista;
	}



	public String getGenere() {
		return genere;
	}



	public void setGenere(String genere) {
		this.genere = genere;
	}



	public Date getData() {
		return data;
	}



	public void setData(Date data) {
		this.data = data;
	}



	public Set<CinefiloDTO> getCinefili() {
		return cinefili;
	}



	public void setCinefili(Set<CinefiloDTO> cinefili) {
		this.cinefili = cinefili;
	}



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
		ProiezioneDTO other = (ProiezioneDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}



	public SalaDTO getSala() {
		return sala;
	}



	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}


}
