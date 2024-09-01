package Beans;

public class Hotel {
	private int codice;
	private int numCam;
	private float prezzo;
	private int camDisp;
	
	public Hotel() {
		super();
		camDisp=0;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public int getNumCam() {
		return numCam;
	}

	public void setNumCam(int numCam) {
		this.camDisp = numCam;
		this.numCam = numCam;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	
}
