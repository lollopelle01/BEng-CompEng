package Beans;

public class Json {

	private String testo;
	private int count;

	public Json() {
		super();
		this.testo = null;
		this.count = 0;
	}
	
	public Json(String testo) {
		super();
		this.testo = testo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
