package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaPaP extends Linea{
	public LineaPaP(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) throws IllegalArgumentException {
		super(id, orariPassaggioAlleFermate);
		if(this.isCircolare()) {
			throw new IllegalArgumentException();
		}
	}
	
	public Optional<Percorso> getPercorso(String fermataDa, String fermataA) {
		try {
			int da=this.getOrarioPassaggioAllaFermata(fermataDa);
			int a=this.getOrarioPassaggioAllaFermata(fermataA);
			int durata=a-da;
			if(durata>0) {
				return Optional.of(new Percorso(fermataA, fermataDa, this, durata));
			}
			else {
				return Optional.empty();
			}
		}
		catch(IllegalArgumentException e) {
			return Optional.empty();
		}
		
	}

}
