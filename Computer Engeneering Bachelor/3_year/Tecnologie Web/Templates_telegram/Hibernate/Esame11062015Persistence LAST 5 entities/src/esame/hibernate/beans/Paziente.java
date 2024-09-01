package esame.hibernate.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Paziente implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codicefiscale;
	private String nome;
	private String cognome;
	private String sesso;
	private Set<RichiestaMedica> richiesteMediche = new HashSet<>();
	
	public Paziente() {
		
	}
	
	public Paziente(String codicefiscale, String nome, String cognome, String sesso) {
		super();
		this.codicefiscale = codicefiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodicefiscale() {
		return codicefiscale;
	}
	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Set<RichiestaMedica> getRichiesteMediche() {
		return richiesteMediche;
	}

	public void setRichiesteMediche(Set<RichiestaMedica> richiesteMediche) {
		this.richiesteMediche = richiesteMediche;
	}
}