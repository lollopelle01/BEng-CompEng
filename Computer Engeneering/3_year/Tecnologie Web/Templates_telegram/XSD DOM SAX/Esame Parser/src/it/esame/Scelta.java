package it.esame;

import java.util.ArrayList;
import java.util.List;

public class Scelta {
	
	private List<Capo> capi = new ArrayList<Capo>();
	private String tipo;
	
	public Scelta() {
		
	}
	
	public List<Capo> getCapi() {
		return capi;
	}
	
	public void addCapo(Capo capo) {
		capi.add(capo);
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
