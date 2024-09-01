package ticketsostaevoluto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import ticketsosta.Tariffa;
import ticketsosta.Ticket;

public class ParcometroEvoluto {
	private Tariffa tariffa[];
	private double calcolaCosto(double CostoOrario, LocalTime da, LocalTime a, int giorno) { 
		if(a.isBefore(da) || LocalTime.of(0, 0).equals(a)){
            a = a.plusHours(24);
        }
        Duration duration = Duration.between(da, a);
        return CostoOrario*duration.toHoursPart() + (CostoOrario*duration.toMinutesPart())/60;
		
	}
	public double calcolaCostoSuPiuGiorni(LocalDateTime inizio, LocalDateTime fine) { //ci vuole un ciclo for per spaziare tra i giorni
		double result=0;
		int giorno=inizio.getDayOfWeek().getValue()-1;
		
		if(inizio.toLocalDate().isBefore(fine.toLocalDate())) {
			double costoOrario=tariffa[giorno].getTariffaOraria();
			LocalTime da=inizio.toLocalTime().plus(tariffa[giorno].getMinutiFranchigia(), ChronoUnit.MINUTES);
			LocalTime a=LocalTime.of(23,59);
			
			result=result+calcolaCosto(costoOrario, da, a, giorno)+tariffa[giorno].getTariffaOraria()*Duration.ofMinutes(1).toMinutes()/60;
			
			LocalDate currentDate=inizio.toLocalDate().plusDays(1);
			while(currentDate.isBefore(fine.toLocalDate())) {
				result=result+tariffa[giorno].getTariffaOraria()*24;
				currentDate=currentDate.plusDays(1);
			}
			
			result=result+calcolaCosto(tariffa[giorno].getTariffaOraria(), LocalTime.of(0, 0), fine.toLocalTime(), giorno);
		}
		else {
			if(Duration.between(inizio, fine).toMinutes()<tariffa[giorno].getDurataMinima())
				return tariffa[giorno].getTariffaOraria();
			else {
				return calcolaCosto(tariffa[giorno].getTariffaOraria(), inizio.toLocalTime().plus(tariffa[giorno].getMinutiFranchigia(), ChronoUnit.MINUTES), fine.toLocalTime(), giorno);
			}
		}
		return result;
	}
	
	public ParcometroEvoluto(Tariffa tariffa[]) {
		this.tariffa=tariffa;
	}
	public TicketEvoluto emettiTicket(LocalDateTime inizio, LocalDateTime fine) { //ci vuole un ciclo for per spaziare tra i giorni
		return new TicketEvoluto(inizio, fine, calcolaCostoSuPiuGiorni(inizio, fine));
	}
	
}
