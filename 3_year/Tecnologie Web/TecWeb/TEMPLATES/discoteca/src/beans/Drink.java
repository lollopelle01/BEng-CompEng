package beans;

public class Drink {

	private String nome;
	private int costo;
	private boolean consegnato;
	
	public Drink(String nome, int costo) {
		this.consegnato = false;
		this.costo = costo;
		this.nome=nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public boolean isConsegnato() {
		return consegnato;
	}

	public void setConsegnato(boolean consegnato) {
		this.consegnato = consegnato;
	}
	
	
}
