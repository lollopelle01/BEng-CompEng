package esame.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MaterialeFisico implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codiceMateriale;
	private String nome;
	private String descrizione;
	private Date dataCreazione;
	private Set<OggettoDigitale> oggettiDigitali = new HashSet<>();
	
	public MaterialeFisico() {
		
	}
	
	
	
	public MaterialeFisico(String codiceMateriale, String nome, String descrizione, Date dataCreazione) {
		this();
		this.codiceMateriale = codiceMateriale;
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataCreazione = dataCreazione;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodiceMateriale() {
		return codiceMateriale;
	}
	public void setCodiceMateriale(String codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Date getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public Set<OggettoDigitale> getOggettiDigitali() {
		return oggettiDigitali;
	}
	public void setOggettiDigitali(Set<OggettoDigitale> oggettiDigitali) {
		this.oggettiDigitali = oggettiDigitali;
	}
	
	// --- utilities ----------------------------

//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = (prime * result + id);
//			return result;
//		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MaterialeFisico other = (MaterialeFisico) obj;
			if (id != other.id)
				return false;
			return true;
		}
	
}