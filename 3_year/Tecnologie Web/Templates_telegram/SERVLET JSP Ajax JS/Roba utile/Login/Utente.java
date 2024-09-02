package beans;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	
	private String nome;
	private String cognome;
	private List<String> documentiSelezionati = new ArrayList<>();
	
	public Utente() {
		
	}
	
	public Utente(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
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
	public List<String> getDocumentiSelezionati() {
		return documentiSelezionati;
	}
	
	public void addDocumento (String documento){
		documentiSelezionati.add(documento);
	}

		@Override
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
		Utente other = (Utente) obj;
		if (!nome.equals(other.nome) || !cognome.equals(other.cognome))
			return false;
		return true;
	}
	

}
