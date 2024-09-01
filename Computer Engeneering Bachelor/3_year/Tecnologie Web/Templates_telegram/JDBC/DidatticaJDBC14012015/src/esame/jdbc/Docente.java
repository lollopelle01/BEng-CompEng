package esame.jdbc;

import java.io.Serializable;

public class Docente implements Serializable{
	private static final long serialVersionUID = 1L;

	private String MatricolaDocente;
	private String cognome;
	private String nome;
	
	public Docente() {
		
	}
	
	
	public Docente(String matricolaDocente, String cognome, String nome) {
		super();
		MatricolaDocente = matricolaDocente;
		this.cognome = cognome;
		this.nome = nome;
	}


	public String getMatricolaDocente() {
		return MatricolaDocente;
	}
	public void setMatricolaDocente(String matricolaDocente) {
		MatricolaDocente = matricolaDocente;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}