package beans;

public class Prodotto {
	
	private String id;
	private String nome;
	private String descrizione;
	private int numDisponibili;
	private int numVenduti;
	private double prezzo;
	
	public Prodotto() {
		
	}
	
	public Prodotto(String id, String nome, String descrizione, int numDisponibili, double prezzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.numDisponibili = numDisponibili;
		this.prezzo = prezzo;
		this.numVenduti = 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public int getNumDisponibili() {
		return numDisponibili;
	}
	public void setNumDisponibili(int numDisponibili) {
		this.numDisponibili = numDisponibili;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getNumVenduti() {
		return numVenduti;
	}

	public synchronized boolean vendi(int numVenduti) {
		if (this.numDisponibili > numVenduti) {
			this.numVenduti += numVenduti;
			this.numDisponibili -= numVenduti;
			return true;
		}
		else
			return false;
	}
	
	

}
