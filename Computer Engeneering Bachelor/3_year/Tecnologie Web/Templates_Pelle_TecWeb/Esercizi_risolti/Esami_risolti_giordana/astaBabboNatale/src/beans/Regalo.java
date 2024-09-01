package beans;

public class Regalo {

	private String nome;
	private int base;
	private String descrizione;
	
	public Regalo(String nome, int base, String descrizione) {
		this.base=base;
		this.descrizione=descrizione;
		this.nome=nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
