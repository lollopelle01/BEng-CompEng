package beans;

public class Tennista {
	private String nome;
	private int rank_atp;
	private int titoli_vinti;
	private int partite_vinte;
	private int partite_perse;
	
	public Tennista(String nome, int rank_atp, int titoli_vinti, int partite_vinte, int partite_perse) { // costruttore per debug
		super();
		this.nome = nome;
		this.rank_atp = rank_atp;
		this.titoli_vinti = titoli_vinti;
		this.partite_vinte = partite_vinte;
		this.partite_perse = partite_perse;
	}

	public Tennista() { // costruttore per bean
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getRank_atp() {
		return rank_atp;
	}

	public void setRank_atp(int rank_atp) {
		this.rank_atp = rank_atp;
	}

	public int getTitoli_vinti() {
		return titoli_vinti;
	}

	public void setTitoli_vinti(int titoli_vinti) {
		this.titoli_vinti = titoli_vinti;
	}

	public int getPartite_vinte() {
		return partite_vinte;
	}

	public void setPartite_vinte(int partite_vinte) {
		this.partite_vinte = partite_vinte;
	}

	public int getPartite_perse() {
		return partite_perse;
	}

	public void setPartite_perse(int partite_perse) {
		this.partite_perse = partite_perse;
	}
}
