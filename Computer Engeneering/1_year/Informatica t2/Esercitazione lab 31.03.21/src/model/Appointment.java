package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Appointment {
	private String description;
	private LocalDateTime from;
	private LocalDateTime to;
	
	public Appointment( String description, LocalDateTime from, LocalDateTime to) {
		this.description=description;
		this.from=from;
		this.to=to;
	}
	public Appointment(String description, LocalDateTime from, Duration durata) {
		this.description=description;
		this.from=from;
		this.to=from.plus(durata);
	}
	
	public String getDescription() {
		return this.description;
	}
	public LocalDateTime getFrom() {
		return this.from;
	}
	public LocalDateTime getTo() {
		return this.to;
	}
	public Duration getDuration() {
		return Duration.between(from, to);
	}
	public void setDescription(String description) {
		this.description=description;
	}
	public void setFrom(LocalDateTime from) {
		this.from=from;
	}
	public void setTo(LocalDateTime to) {
		this.to=to;
	}
	public void setDuration(Duration durata) {
		this.getFrom().plus(durata);
	}
	
	public boolean equals(Appointment a) {
		return (this.getDescription()==a.getDescription() && this.getFrom().isEqual(a.getFrom()) && this.getTo().isEqual(a.getTo()));
	}
	
	@Override
	public String toString() {
		DateTimeFormatter dtf=DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
		return "Impegno: "+this.getDescription()+"\nInizio: "+this.getFrom().format(dtf)+"\nFine: "+this.getTo().format(dtf)+"\n\n";
	}
	
}
