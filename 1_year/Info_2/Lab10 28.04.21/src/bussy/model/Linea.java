package bussy.model;

import java.util.*;
import java.util.Map.Entry;

public abstract class Linea {
	private String id;
	private Map<Integer, Fermata> orariPassaggioAlleFermate;
	
	public Linea(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		if (id==null || id.contentEquals("") || orariPassaggioAlleFermate==null)
			throw new IllegalArgumentException("arg invalidi in ctor linea");
		this.id = id;
		this.orariPassaggioAlleFermate = orariPassaggioAlleFermate;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Map<Integer, Fermata> getOrariPassaggioAlleFermate(){
		 return this.orariPassaggioAlleFermate;
	 }
	
	public int getOrarioPassaggioAllaFermata(String nomeFermata) throws IllegalArgumentException {
		Optional<Map.Entry<Integer,Fermata>> optionalEntry = Optional.empty();
		for (Map.Entry<Integer,Fermata> entry : orariPassaggioAlleFermate.entrySet()) {
			if (entry.getValue().getNome().equals(nomeFermata)) {
				optionalEntry = Optional.of(entry);
				break;
			}
		}
		if (!optionalEntry.isPresent()) throw new IllegalArgumentException("Non esiste " + nomeFermata + " nella linea " + this.getId());
		return optionalEntry.get().getKey();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orariPassaggioAlleFermate == null) ? 0 : orariPassaggioAlleFermate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orariPassaggioAlleFermate == null) {
			if (other.orariPassaggioAlleFermate != null)
				return false;
		} else if (!orariPassaggioAlleFermate.equals(other.orariPassaggioAlleFermate))
			return false;
		return true;
	}

	public Entry<Integer, Fermata> getCapolineaIniziale() throws IllegalArgumentException{
		Optional<Entry<Integer,Fermata>> entryIniziale = Optional.empty();
		for (Entry<Integer,Fermata> entry : this.getOrariPassaggioAlleFermate().entrySet()) {
			if (!entryIniziale.isPresent() || entry.getKey() < entryIniziale.get().getKey()) {
				entryIniziale = Optional.of(entry);
			}
		}

		if (entryIniziale.isPresent()) return entryIniziale.get();
		else throw new IllegalArgumentException("lista fermate vuota o illegale");
	}
	
	public Entry<Integer, Fermata> getCapolineaFinale() throws IllegalArgumentException{
		Optional<Entry<Integer,Fermata>> entryFinale = Optional.empty();
		for (Entry<Integer,Fermata> entry : this.getOrariPassaggioAlleFermate().entrySet()) {
			if (!entryFinale.isPresent() || entry.getKey() > entryFinale.get().getKey()) {
				entryFinale = Optional.of(entry);
			}
		}

		if (entryFinale.isPresent()) return entryFinale.get();
		else throw new IllegalArgumentException("lista fermate vuota o illegale");
	}
	
	public abstract Optional<Percorso> getPercorso(String fermataDa, String fermataA);
	
	public boolean isCapolineaFinale(String fermata) {
		return fermata.equals(this.getCapolineaFinale().getValue().getNome());
	}
	
	public boolean isCapolineaIniziale(String fermata) {
		return fermata.equals(this.getCapolineaIniziale().getValue().getNome());

	}
	
	public boolean isCircolare() {
		return this.getCapolineaIniziale().getValue().getNome().equals(this.getCapolineaFinale().getValue().getNome());
	}
	
	@Override
	public String toString() {
		return "ID linea : "+this.id+"\nOrario fermate linea: "+this.orariPassaggioAlleFermate;
	}
	
}
