package bussy.model;

import java.util.Map;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;

public class MyCercatore implements Cercatore{
	private Map<String, Linea> mappaLinee;
	
	@Override
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax) {
		SortedSet<Percorso> result=new TreeSet<>();
		for(Linea l: this.mappaLinee.values()) {
			if(durataMax.isPresent()) {
				if(l.getPercorso(fermataDa, fermataA).isPresent() && l.getPercorso(fermataDa, fermataA).get().getDurata()<=durataMax.getAsInt()) {
					result.add(l.getPercorso(fermataDa, fermataA).get());
				}
			}
			else {
				if(l.getPercorso(fermataDa, fermataA).isPresent()){
					result.add(l.getPercorso(fermataDa, fermataA).get());
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Linea> getMappaLinee() {
		return this.mappaLinee;
	}

	public MyCercatore(Map<String, Linea> mappaLinee) {
		super();
		this.mappaLinee = mappaLinee;
	}
	

}
