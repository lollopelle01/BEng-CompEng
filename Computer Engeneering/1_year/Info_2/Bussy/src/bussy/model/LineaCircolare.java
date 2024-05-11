package bussy.model;

import java.util.Map;
import java.util.Optional;

public class LineaCircolare extends Linea{

	public LineaCircolare(String id, Map<Integer, Fermata> orariPassaggioAlleFermate) {
		super(id, orariPassaggioAlleFermate);
		if (!this.isCircolare()) { // allora è linea circolare!
			throw new BadLineException("linea non circolare");
		}
	}
	
	private boolean isCapolinea(String s) {
		return this.isCapolineaFinale(s) && this.isCapolineaIniziale(s);
	}

	@Override
	public Optional<Percorso> getPercorso(String fermataDa, String fermataA) {
		try {
			int durata=0;
			if(this.isCapolinea(fermataDa) && !this.isCapolinea(fermataA)) {
				durata=this.getOrarioPassaggioAllaFermata(fermataA);
				return durata>0? Optional.of(new Percorso(fermataDa, fermataA, this, durata)): Optional.empty();
			}
			
			else if(!this.isCapolinea(fermataDa) && this.isCapolinea(fermataA)) {
				durata=this.getCapolineaFinale().getKey()-this.getOrarioPassaggioAllaFermata(fermataDa);
				return durata>0? Optional.of(new Percorso(fermataDa, fermataA, this, durata)): Optional.empty();
			}
			
			else if(this.isCapolinea(fermataDa) && this.isCapolinea(fermataA))
				return Optional.of(new Percorso(fermataDa, fermataA, this, this.getCapolineaFinale().getKey()));
			
			else if(!this.isCapolinea(fermataDa) && !this.isCapolinea(fermataA)) {
				durata=this.getOrarioPassaggioAllaFermata(fermataA)-this.getOrarioPassaggioAllaFermata(fermataDa);
				if(durata>0) {
					return Optional.of(new Percorso(fermataDa, fermataA, this, durata));
				}
				else {
					return Optional.of(new Percorso(fermataDa, fermataA, this, this.getCapolineaFinale().getKey()+durata));
				}
			}
			else
				return Optional.empty();
		}
		catch(Exception e) {
			return Optional.empty();
		}
	}

}
