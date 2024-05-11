package ticketsosta;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Parcometro {
	private Tariffa tariffa;
	private double calcolaCosto(double CostoOrario, LocalTime da, LocalTime a) { 
		Duration durata=Duration.between(da, a);
		
		if (a.isBefore(da) || LocalTime.of(0, 0).equals(a)) {
			return Double.NaN;
		}
		if(tariffa.getMinutiFranchigia()>0){
            durata = durata.minus(tariffa.getMinutiFranchigia(), ChronoUnit.MINUTES);
        }
        if(tariffa.getDurataMinima()>durata.toMinutes())
            return tariffa.getTariffaOraria();

        return CostoOrario*durata.toHoursPart() + (CostoOrario*durata.toMinutesPart())/60;
		
	}
	
	public Parcometro(Tariffa tariffa) {
		this.tariffa=tariffa;
	}
	public Ticket emettiTicket(LocalTime inizio, LocalTime fine) { 
		Ticket result;
		double costo=this.calcolaCosto(this.tariffa.getTariffaOraria(), inizio, fine);
		if(costo==Double.NaN)
			result=null;
		else
			result=new Ticket(inizio, fine, costo);
		return result;
	}
	
}
