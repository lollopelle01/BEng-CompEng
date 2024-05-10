package zannopoll.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SondaggioPercentuale extends Sondaggio{
	private Map<String, Double> mappePercentuali;

	public SondaggioPercentuale(List<Intervista> listaInterviste) {
		super(listaInterviste);
		this.mappePercentuali=new TreeMap<String, Double>();
		for(String scelta: this.getMappaInterviste().keySet()) {
			this.mappePercentuali.put(scelta, (this.getMappaInterviste().get(scelta).size()/(double)this.getTotaleIntervistati()) );
		}
	}
	
	public Map<String, Double> getMappaPercentuali(){
		return this.mappePercentuali;
	}
	
	public double getPercentualeIntervistati(String scelta) {
		if(scelta==null|| scelta.isEmpty())
			throw new IllegalArgumentException();
		if(this.mappePercentuali.get(scelta)==null)
			throw new IllegalArgumentException();
		 return this.mappePercentuali.get(scelta);
	}
	
	public Optional<SondaggioPercentuale> getSondaggioFiltrato(int minAge, int maxAge, Optional<Sesso> maybeSex){
		try {
			var result=Optional.of(new SondaggioPercentuale(this.getIntervisteFiltrate(minAge, maxAge, maybeSex)));
			return result;
		}
		catch(IllegalArgumentException e) {
			return Optional.empty();
		}
	}
	
	public List<Intervista> getIntervisteFiltrate(int minAge, int maxAge, Optional<Sesso> maybeSex){
		List<Intervista> result=new ArrayList<>();
		Map<String, List<Intervista>> interviste =this.getMappaInterviste();
		for(String scelta: interviste.keySet()) {
			for(Intervista i: interviste.get(scelta)) {
				if(maybeSex.isPresent()) {
					if(i.getEta()<=maxAge && i.getEta()>=minAge && maybeSex.get().equals(i.getSesso())) {
						result.add(i);
					}
				}
				else {
					if(i.getEta()<=maxAge && i.getEta()>=minAge) {
						result.add(i);
					}
				}
			}
		}
		return result;
	}

}
