package beans;

public class Hotel {
	private int id;
	private int camere_totali;
	private float prezzo_statico_camere;
	private int camere_disponibili;
	
	public Hotel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCamere_totali() {
		return camere_totali;
	}

	public void setCamere_totali(int camere_totali) {
		this.camere_totali = camere_totali;
	}

	public float getPrezzo_statico_camere() {
		return prezzo_statico_camere;
	}

	public void setPrezzo_statico_camere(float prezzo_statico_camere) {
		this.prezzo_statico_camere = prezzo_statico_camere;
	}

	public int getCamere_disponibili() {
		return camere_disponibili;
	}

	public void setCamere_disponibili(int camere_disponibili) {
		this.camere_disponibili = camere_disponibili;
	}
	
	
}
