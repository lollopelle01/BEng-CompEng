package Beans;

//classe contentere i risultati delle operazioni svolte dalla Servlet e dal SlaveCounterBean
public class CounterResult {
	
	private int cambiati = 0;
	
	public int getCambiati() {
		return cambiati;
	}
	public void setCambiati(int serverCounter) {
		this.cambiati = serverCounter;
	}
	
	public CounterResult() {
		super();
	}

}
