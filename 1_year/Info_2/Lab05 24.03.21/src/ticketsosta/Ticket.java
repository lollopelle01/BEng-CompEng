package ticketsosta;

import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Formatter;
import java.util.Locale;

public class Ticket {
	private double costo;
	private LocalTime inizio;
	private LocalTime fine;
	
	public double getCosto(){
		return this.costo;
	}
	public String getCostoAsString() {
		NumberFormat formatter=NumberFormat.getCurrencyInstance(Locale.ITALY);
		return formatter.format(this.getCosto());
	}
	public LocalTime getFineSosta() {
		return fine;
	}
	public LocalTime getInizioSosta() {
		return inizio;
	}
	
	public Ticket( LocalTime inizio, LocalTime fine, double costo) {
		this.costo=costo;
		this.fine=fine;
		this.inizio=inizio;
	}
	
	private String toStringDuration(Duration duration) {
		int minuti=duration.toMinutesPart();
		String sMinuti=(minuti<10 ? "0" : "");
		return duration.toHoursPart()+sMinuti;
		
	}
	
	@Override
	public String toString() {
		return "Sosta autorizzata dalle"+inizio.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY))+
				"alle"+fine.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY))+
				"\nDurata:"+ toStringDuration(Duration.between(inizio, fine));
	}
	
}
