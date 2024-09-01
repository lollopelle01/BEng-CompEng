package it.unibo.paw.model;

import java.io.Serializable;
import java.util.Set;

public class Supermercato implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codiceSuper;
	private String nome;
	private int ratingGradimento;
	
	private Set<ProdottoOfferto> prodottiOfferti;

	public Supermercato() {
		super();
	}

	public int getCodiceSuper() {
		return codiceSuper;
	}

	public void setCodiceSuper(int codiceSuper) {
		this.codiceSuper = codiceSuper;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getRatingGradimento() {
		return ratingGradimento;
	}

	public void setRatingGradimento(int ratingGradimento) {
		this.ratingGradimento = ratingGradimento;
	}

	public Set<ProdottoOfferto> getProdottiOfferti() {
		return prodottiOfferti;
	}

	public void setProdottiOfferti(Set<ProdottoOfferto> prodottiOfferti) {
		this.prodottiOfferti = prodottiOfferti;
	}
	
}
