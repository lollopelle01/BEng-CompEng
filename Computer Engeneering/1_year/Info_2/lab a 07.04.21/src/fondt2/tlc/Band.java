package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import fondt2.tlc.util.DayOfWeekHelper;

public class Band {
	private DayOfWeek[] combinedDays;
	private LocalTime startTime;
	private LocalTime endTime;
	private double costPerInterval;
	
	public Band(LocalTime start, LocalTime end, DayOfWeek[] giorni, double costo) {
		this.startTime=start;
		this.endTime=end;
		this.costPerInterval=costo;
		this.combinedDays=giorni;
	}
	
	public DayOfWeek[] getCombinedDays() {
		return combinedDays;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public double getCostPerInterval() {
		return costPerInterval;
	}
	
	public boolean isInBand(LocalDateTime dateTime) {
		boolean result1=false;
		boolean result2=false;
		DayOfWeek gWeek=dateTime.getDayOfWeek();
		LocalTime hWeek=dateTime.toLocalTime();
		if(DayOfWeekHelper.isDayIn(gWeek, combinedDays))
			result1=true;
		
		if(hWeek.isAfter(this.startTime) && hWeek.isBefore(this.endTime))
			result2=true;
		
		return result1 && result2;
	}
	public boolean isValid() {
		boolean result1=false;
		boolean result2=false;
		boolean result3=false;
		int j=0;
		if(!endTime.isBefore(startTime))
			result1=true;
		
		if(this.combinedDays!=null && this.getCombinedDays().length!=0)
			result2=true;
		
		if(this.costPerInterval>=0)
			result3=true;
		
		return result1&&result2&&result3;
	}

	
}
