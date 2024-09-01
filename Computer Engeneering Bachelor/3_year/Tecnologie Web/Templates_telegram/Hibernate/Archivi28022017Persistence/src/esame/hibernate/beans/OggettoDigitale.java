package esame.hibernate.beans;

import java.io.Serializable;
import java.util.Date;

public class OggettoDigitale implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codiceOggetto;
	private String nome;
	private String formato;
	private Date dataDigitalizzazione;
	
	public OggettoDigitale() {
		
	}
	
	public OggettoDigitale(String codiceOggetto, String nome, String formato, Date dataDigitalizzazione) {
		this();
		this.codiceOggetto = codiceOggetto;
		this.nome = nome;
		this.formato = formato;
		this.dataDigitalizzazione = dataDigitalizzazione;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceOggetto() {
		return codiceOggetto;
	}

	public void setCodiceOggetto(String codiceOggetto) {
		this.codiceOggetto = codiceOggetto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Date getDataDigitalizzazione() {
		return dataDigitalizzazione;
	}

	public void setDataDigitalizzazione(Date dataDigitalizzazione) {
		this.dataDigitalizzazione = dataDigitalizzazione;
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
			OggettoDigitale other = (OggettoDigitale) obj;
			if (id != other.id)
				return false;
			return true;
		}
}