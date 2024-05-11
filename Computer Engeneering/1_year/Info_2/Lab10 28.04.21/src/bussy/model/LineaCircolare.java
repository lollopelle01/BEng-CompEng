package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaCircolare extends Linea{
	public LineaCircolare(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) throws IllegalArgumentException {
		super(id, orariPassaggioAlleFermate);
		if(!this.isCircolare()) {
			throw new IllegalArgumentException();
		}
	}
	
	private boolean isCapolinea(String fermata) {
		return this.isCapolineaFinale(fermata) && this.isCapolineaIniziale(fermata);
	}

	@Override
	public Optional<Percorso> getPercorso(String fermataDa, String fermataA){
		int durata;
		try {
			if(!this.isCapolinea(fermataA) && this.isCapolinea(fermataDa)) {
				durata=this.getOrarioPassaggioAllaFermata(fermataA);
			}
			if(this.isCapolinea(fermataA) && !this.isCapolinea(fermataDa)) {
				durata=this.getCapolineaFinale().getKey().intValue()- this.getOrarioPassaggioAllaFermata(fermataDa);
			}
			if(this.isCapolinea(fermataA) && this.isCapolinea(fermataDa)) {
				durata=this.getCapolineaFinale().getKey().intValue();
			}
			else {
				int durataDa=this.getOrarioPassaggioAllaFermata(fermataDa);
				int durataA=this.getOrarioPassaggioAllaFermata(fermataA);
				if(durataA>durataDa) {
					durata=durataA-durataDa;
				}
				else {
					durata=(durataA-durataDa)+this.getCapolineaFinale().getKey().intValue();
				}
			}
			return Optional.of(new Percorso(fermataDa, fermataA, this, durata));
		}
		catch(IllegalArgumentException e) {
			return Optional.empty();
		}
	}
 }