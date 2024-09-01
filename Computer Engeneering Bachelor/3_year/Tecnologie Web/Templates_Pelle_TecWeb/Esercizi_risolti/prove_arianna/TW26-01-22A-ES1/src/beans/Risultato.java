package beans;

import java.io.Serializable;

public class Risultato implements Serializable {

	private static final long serialVersionUID = 1L;

    private String text;
    private int conteggio;
    
	// --- constructor ----------
	public Risultato(String txt, int cont) {
        this.text = txt;
        this.conteggio = cont;
	}

	// --- getters and setters --------------
	public String getText() {
        return this.text;
    }
    public void setText(String txt) {
        this.text = txt;
    }
	
	public int getConteggio() {
		return this.conteggio;
	}
	public void setConteggio(int cont) {
		this.conteggio = cont;
	}

}
