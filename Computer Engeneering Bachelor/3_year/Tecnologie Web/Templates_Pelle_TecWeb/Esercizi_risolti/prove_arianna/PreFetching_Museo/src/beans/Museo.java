package beans;

import java.io.Serializable;

import java.util.*;

public class Museo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Stanza> museo;
	
	public Museo() {
		super();
		this.museo = new ArrayList<>();
		
		//aggiunta di alcune stanze...
	}

	public List<Stanza> getMuseo() {
		return museo;
	}

	public void setMuseo(List<Stanza> dbItems) {
		this.museo = dbItems;
	}
}
