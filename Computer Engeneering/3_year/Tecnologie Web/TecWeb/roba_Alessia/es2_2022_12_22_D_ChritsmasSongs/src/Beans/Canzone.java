package Beans;

public class Canzone {
	
	private String nome;
	private String formato;
	private double min;
	private String binario;
	
	public Canzone() {
		super();
	}

	public Canzone(String nome, double min, String formato, String binario) {
		super();
		this.nome = nome;
		this.formato = formato;
		this.binario = binario;
		this.min = min;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public String getBinario() {
		return binario;
	}

	public void setBinario(String binario) {
		this.binario = binario;
	}


}
