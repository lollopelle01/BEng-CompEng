package beans;

public class Result {
	private String message;
	private int prezzo;
	private int carta;
	private int prezzo_t;
	
	public Result() {
		this.message = "";
		this.carta = 0;
		this.prezzo = 0;
		this.prezzo_t=0;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public int getCarta() {
		return carta;
	}

	public void setCarta(int carta) {
		this.carta = carta;
	}

	public int getPrezzo_t() {
		return prezzo_t;
	}

	public void setPrezzo_t(int prezzo_t) {
		this.prezzo_t = prezzo_t;
	}
	
	
	
	
}
