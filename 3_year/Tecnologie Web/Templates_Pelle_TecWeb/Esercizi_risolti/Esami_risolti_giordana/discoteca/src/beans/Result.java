package beans;

public class Result {
	private String message;
	private int totale;
	
	public Result() {
		this.message = "";
		this.totale =0;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotale() {
		return totale;
	}

	public void setTotale(int totale) {
		this.totale = totale;
	}
	
	
	
}
