package beans;

import java.io.Serializable;
import java.util.List;

public class Stanza implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Opera> opere;  //max 4
	private int number;
	
	public Stanza(int number) {
		this.number= number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public List<Opera> getOpere() {
		return this.opere;
	}
	public void setOpere(List<Opera> l) {
		this.opere = l;
	}
}
