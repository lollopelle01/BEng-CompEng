package esame.hibernate.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Struttura implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String codice;
	private String nome;
	private String indirizzo;
	private Set<TipoAccertamento> tipiaccertamento = new HashSet<>();
	
	public Struttura() {
		
	}
	
	public Struttura(String codice, String nome, String indirizzo) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.indirizzo = indirizzo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Set<TipoAccertamento> getTipiaccertamento() {
		return tipiaccertamento;
	}
	public void setTipiaccertamento(Set<TipoAccertamento> tipiaccertamento) {
		this.tipiaccertamento = tipiaccertamento;
	}
	
	
}