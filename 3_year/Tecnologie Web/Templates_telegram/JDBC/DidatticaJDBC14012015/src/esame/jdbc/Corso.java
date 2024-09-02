package esame.jdbc;

import java.io.Serializable;

public class Corso implements Serializable{
	private static final long serialVersionUID = 1L;

	private String CodiceCorso;
	private String nome;
	private int creditiFormativi;
	
	public Corso() {
		
	}
	
	
	public Corso(String codiceCorso, String nome, int creditiFormativi) {
		super();
		CodiceCorso = codiceCorso;
		this.nome = nome;
		this.creditiFormativi = creditiFormativi;
	}


	public String getCodiceCorso() {
		return CodiceCorso;
	}
	public void setCodiceCorso(String codiceCorso) {
		CodiceCorso = codiceCorso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCreditiFormativi() {
		return creditiFormativi;
	}
	public void setCreditiFormativi(int creditiFormativi) {
		this.creditiFormativi = creditiFormativi;
	}
	
	
}