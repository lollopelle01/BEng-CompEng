package bussy.model;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;

public class MyCercatore implements Cercatore{
	private Map<String, Linea> mappaLinee;
	

	@Override
	public SortedSet<Percorso> cercaPercorsi(String fermataDa, String fermataA, OptionalInt durataMax) {
		if(fermataA==null || fermataDa==null)
			throw new IllegalArgumentException();
		
		SortedSet<Percorso> percorsiDiretti=new TreeSet<Percorso>();
		for(Linea l: mappaLinee.values()) {
			Optional<Percorso> percorsoOpt=l.getPercorso(fermataDa, fermataA);
			if(percorsoOpt.isPresent()) {
				Percorso percorso=percorsoOpt.get();
				if(percorso.getDurata()>0) {
					percorsiDiretti.add(percorso);
				}
			}
		}
		
		SortedSet<Percorso> percorsiDirettiNonPiuLunghiDiTot;
		if(durataMax.isPresent()) {
			percorsiDirettiNonPiuLunghiDiTot=new TreeSet<Percorso>();
			for(Percorso percorso: percorsiDiretti) {
				if(percorso.getDurata()<=durataMax.getAsInt()) {
					percorsiDirettiNonPiuLunghiDiTot.add(percorso);
				}
			}
		}
		else {
			percorsiDirettiNonPiuLunghiDiTot=percorsiDiretti;
		}
		
		return percorsiDirettiNonPiuLunghiDiTot;
	}

	@Override
	public Map<String, Linea> getMappaLinee() {
		return this.mappaLinee;
	}
	
	public MyCercatore( Map<String, Linea> mappaLinee) {
		if(mappaLinee==null || mappaLinee.isEmpty())
			throw new IllegalArgumentException();
		this.mappaLinee=mappaLinee;
	}
	
}
