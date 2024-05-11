package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PartitaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int codicePartita;
	private String categoria;
	private String girone;
	private String nomeSquadraCasa;
	private String nomeSquadraOspite;
	private Date data;
	
	private StadioDTO stadio;
	
	
	// --- constructor ----------
	
	public PartitaDTO() {
	}
	
	public PartitaDTO(String categoria, String girone, String nomeSquadraCasa, String nomeSquadraOspite, Date data) {
		this.categoria = categoria;
		this.girone = girone;
		this.nomeSquadraCasa = nomeSquadraCasa;
		this.nomeSquadraOspite = nomeSquadraOspite;
		this.data = data;
	}
	
	
	// --- getters and setters --------------
	
	public int getCodicePartita() {
		return codicePartita;
	}

	public void setCodicePartita(int codicePartita) {
		this.codicePartita = codicePartita;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getGirone() {
		return girone;
	}

	public void setGirone(String girone) {
		this.girone = girone;
	}

	public String getNomeSquadraCasa() {
		return nomeSquadraCasa;
	}

	public void setNomeSquadraCasa(String nomeSquadraCasa) {
		this.nomeSquadraCasa = nomeSquadraCasa;
	}

	public String getNomeSquadraOspite() {
		return nomeSquadraOspite;
	}

	public void setNomeSquadraOspite(String nomeSquadraOspite) {
		this.nomeSquadraOspite = nomeSquadraOspite;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		PartitaDTO other = (PartitaDTO) obj;
		if (codicePartita != other.codicePartita)
			return false;
		return true;
	}

	public StadioDTO getStadio() {
		return stadio;
	}

	public void setStadio(StadioDTO stadio) {
		this.stadio = stadio;
	}

}
