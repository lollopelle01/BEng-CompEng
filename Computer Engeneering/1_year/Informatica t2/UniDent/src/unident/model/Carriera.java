package unident.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Carriera {
	private SortedMap<AttivitaFormativa, List<Esame>> esami;
	private NumberFormat formattatore;
	
	public Carriera(Set<AttivitaFormativa> pianoDidattico) {
		this.esami=new TreeMap<>(Comparator.comparing(AttivitaFormativa::getNome));
		for(AttivitaFormativa af: pianoDidattico) {
			this.esami.put(af, new ArrayList<Esame>());
		}
		this.formattatore=NumberFormat.getInstance(Locale.ITALY);
		this.formattatore.setMaximumFractionDigits(2);
		this.formattatore.setMinimumFractionDigits(2);
	}
	
	public List<Esame> istanzeDi(AttivitaFormativa af){
		return this.esami.get(af);
	}
	
	public double mediaPesata() {
		double num=0, den=0;
		for(AttivitaFormativa af: this.esami.keySet()) {
			if(this.esami.get(af)!=null) {
				for(Esame e: this.esami.get(af)) {
					OptionalInt voto=e.getVoto().getValue();
					int cfu=e.getAf().getCfu();
					
					if(voto.isPresent()) {
						num=num+voto.getAsInt()*cfu;
						den=den+cfu;
					}
					else
						continue;
				}
			}
		}
		return num/den;
	}
	
	public void registra(Esame e) {
		LocalDate data=e.getDate();
		AttivitaFormativa af=e.getAf();
		List<Esame> esamiL= this.esami.get(af);
		
		if(esamiL==null) {
			throw new IllegalArgumentException("Attività formativa non presente in carriera");
		}
		
		if(!esamiL.isEmpty()) {
			Esame ultimoEsame=esamiL.get(esamiL.size()-1);
			if(ultimoEsame.getVoto().getValue().isPresent()) {
				throw new IllegalArgumentException("Esame già superato con esito positivo");
			}
			
			if(ultimoEsame.getDate().isEqual(data)) {
				throw new IllegalArgumentException("Ultimo esame registrato ha data identica all'attuale");
			}
			
			if(ultimoEsame.getDate().isAfter(data)) {
				throw new IllegalArgumentException("Ultimo esame registrato ha data successiva all'attuale");
			}
		}
		this.esami.get(af).add(e);
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Esami sostenuti:"+System.lineSeparator());
		for(AttivitaFormativa af: this.esami.keySet()) {
			if(!this.esami.get(af).isEmpty()) {
				for(Esame e: this.esami.get(af)) {
					sb.append(e+System.lineSeparator());
				}
			}
		}
		sb.append(System.lineSeparator());
		sb.append("Media pesata: "+this.formattatore.format(this.mediaPesata())+"/30"+System.lineSeparator());
		
		return sb.toString();
	}
}
