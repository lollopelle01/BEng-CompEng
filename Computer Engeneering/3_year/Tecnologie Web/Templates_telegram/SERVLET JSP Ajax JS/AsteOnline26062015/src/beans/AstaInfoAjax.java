package beans;

public class AstaInfoAjax {
	
	private String nomeUtente;
	private String cognomeUtente;
	private String nome;
	private double bestBid;
	
	
	
	public AstaInfoAjax(String nomeUtente, String cognomeUtente, String nome, double bestBid) {
		super();
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.nome = nome;
		this.bestBid = bestBid;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getCognomeUtente() {
		return cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	
	public AstaInfoAjax() {
		
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getBestBid() {
		return bestBid;
	}
	public void setBestBid(double bestBid) {
		this.bestBid = bestBid;
	}

}
