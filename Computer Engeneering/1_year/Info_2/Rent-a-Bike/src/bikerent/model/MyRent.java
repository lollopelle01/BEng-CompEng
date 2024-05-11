package bikerent.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MyRent implements Rent{
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private Rate rate;
	
	private String toString(Duration d) {
		long minuti=d.toMinutes()%60;
		long ore=(d.toMinutes()-minuti)/60;
		return "Ore: "+ore+"Minuti: "+minuti;
	}

	public MyRent(LocalDateTime inizio, LocalDateTime fine, Rate rate) {
		super();
		this.inizio = inizio;
		this.fine = fine;
		this.rate = rate;
	}

	@Override
	public boolean isRegular() {
		Duration d=Duration.between(inizio, fine);

		if(this.rate.getOrarioMax().isPresent()) {
			LocalTime max=this.rate.getOrarioMax().get();
			LocalDateTime Max=LocalDateTime.of(inizio.toLocalDate(), max);
			if(fine.isAfter(Max)) {
				return false;
			}
			return true;
			
		}
		if(this.rate.getDurataMax().isPresent()) {
			Duration max=this.rate.getDurataMax().get();
			if(d.compareTo(max)>0) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public LocalDateTime getStart() {
		return this.inizio;
	}

	@Override
	public LocalDateTime getEnd() {
		return this.fine;
	}
	
	public String toString() {
		return "Rate: "+this.rate.toString()+" Duration: "+this.toString(Duration.between(inizio, fine));
	}
	
}
