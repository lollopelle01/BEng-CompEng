package minirail.model;

import java.util.Map;
import java.util.Set;

public class MyRunner implements Runner{
	
	private Gauge gauge;
	private StringBuilder sb=new StringBuilder();
	private Map<String, LineStatus> lines;
	
	@Override
	public void clock(double period) {
		for(LineStatus l: this.lines.values()) {
			Set<Train> treni= l.getAllTrains();
			for(Train t: treni) {
				this.moveTrain(l, t, period);
			}
		}
	}

	@Override
	public Map<String, LineStatus> getLines() {
		return this.lines;
	}

	@Override
	public Gauge getGauge() {
		return this.gauge;
	}

	public MyRunner(Map<String, LineStatus> lines, Gauge gauge) {
		super();
		this.lines = lines;
		this.gauge = gauge;
	}
	
	private double arrotonda(double importo) {
		return Math.round(100.0*importo)/100.0;
	}
	
	private void moveTrain(LineStatus status, Train treno, double tempo) {
		double locazioneIniziale=status.getTrainLocation(treno);
		double velocita=this.gauge.kmhToCms(treno.getSpeed());
		double locazioneFinaleTmp=velocita*tempo+locazioneIniziale;
		double locazioneFinale=locazioneFinaleTmp%status.getLine().getLength();
		
		this.sb.append("Trying to move "+treno.getName()+
						"from "+this.arrotonda(treno.getSpeed())+
						" km/h from pos "+this.arrotonda(locazioneIniziale)+
						" to pos "+this.arrotonda(locazioneFinale));
		if(status.moveTrain(treno, locazioneFinale)) {
			sb.append(": success\n");
		}
		else
			sb.append(": fail\n");
	}
	
	public String toString() {
		return this.sb.toString();
	}

}
