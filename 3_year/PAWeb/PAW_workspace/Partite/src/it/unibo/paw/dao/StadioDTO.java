package it.unibo.paw.dao;

import java.io.Serializable;
import java.util.*;
import java.util.Date;

public class StadioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	// Attributi del bean da scrivere
	private int id;

	private String codice;
	private String nome;
	private String citta;
	
	private Set<PartitaDTO> partite;

	// --- constructor ----------
		public StadioDTO() {
			super();
		}

	// --- getters and setters --------------
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

		public String getCitta() {
			return citta;
		}

		public void setCitta(String citta) {
			this.citta = citta;
		}

		public Set<PartitaDTO> getPartite() {
			return partite;
		}

		public void setPartite(Set<PartitaDTO> partite) {
			this.partite = partite;
		}
}
