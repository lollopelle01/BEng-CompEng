package beans;

import java.io.Serializable;
import java.util.List;

public class Risultato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Stanza n;
	public Stanza n1;
	public Stanza n2;
	
	public Risultato() {
		this.n = null;
		this.n1 = null;
		this.n2 = null;
	}
	
	public void setStanza(int i, Stanza s) {
		if(i==0)
			n = s;
		else if(i==1)
			n1 = s;
		else 
			n2 = s;
	}
}
