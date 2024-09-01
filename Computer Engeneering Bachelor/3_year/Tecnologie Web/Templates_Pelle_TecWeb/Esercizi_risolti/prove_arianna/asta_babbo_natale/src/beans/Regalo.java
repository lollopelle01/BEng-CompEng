package beans;

import java.io.Serializable;

public class Regalo implements Serializable {

	private static final long serialVersionUID = 1L;

    private String nome;
    private String descrizione;
    private int baseAsta;
	
    
	// --- constructor ----------
	public Regalo(String nome, String desc, int base) {
        this.nome = nome;
        this.descrizione = desc;
        this.baseAsta = base;
	}

	// --- getters and setters --------------
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

	public int getBaseAsta() {
		return baseAsta;
	}

	public void setBaseAsta(int baseAsta) {
		this.baseAsta = baseAsta;
	}


	// --- utilities ----------------------------
    public String toString() {
		return "Regalo [nome=" + nome + ", descrizione=" + descrizione + "base d'asta= "+baseAsta+"]";
	}
}
