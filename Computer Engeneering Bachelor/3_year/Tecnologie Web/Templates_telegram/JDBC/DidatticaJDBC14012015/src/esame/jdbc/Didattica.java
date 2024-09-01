package esame.jdbc;

import java.io.Serializable;

public class Didattica implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String docente;
	private String corso;
	
	public Didattica() {
		
	}
	
	
	public Didattica(String docente, String corso) {
		super();
		this.docente = docente;
		this.corso = corso;
	}


	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public String getCorso() {
		return corso;
	}
	public void setCorso(String corso) {
		this.corso = corso;
	}
	
	
	
	
}
