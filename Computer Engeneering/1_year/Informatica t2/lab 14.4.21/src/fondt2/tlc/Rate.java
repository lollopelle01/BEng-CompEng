package fondt2.tlc;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import fondt2.tlc.util.DayOfWeekHelper;

public class Rate {
	private Band[] bands;
	private int intervalMillis;
	private String name;
	private String numberRoot;
	private double startCallCost;
	
	public Band[] getBands() {
		return bands;
	}
	public String getName() {
		return name;
	}	
	public Rate(String name, Band[] bands, int intervalMillis, double startCallCost, String numberRoot) {
		this.name=name;
		this.bands=bands;
		this.intervalMillis=intervalMillis;
		this.startCallCost=startCallCost;
		this.numberRoot=numberRoot;
	}
	
	public boolean isApplicableTo(String destinationNumber) {
		boolean r1=false;
		if(destinationNumber.startsWith(numberRoot))
			r1=true;
		return r1;
	}
	
	private double getStartCallCost() {
		return this.startCallCost;
	}
	
	private Band[] selectBandsInDay(DayOfWeek day) {
		int counter=0;
		for(int i=0; i<bands.length; i++) {
			if(DayOfWeekHelper.isDayIn(day, bands[i].getCombinedDays())) {
				counter++;
			}
		}
		var newBand=new Band[counter];
		int j=0;
		for(int i=0; i<bands.length; i++) {
			if(DayOfWeekHelper.isDayIn(day, bands[i].getCombinedDays())) {
				newBand[j]=bands[i];
				j++;
			}
		}
		return newBand;
	}
	private void sortBandsByStartTime(Band[] bands) {
		for (int i = 0; i < bands.length ; i++)
            for (int j = i+1; j < bands.length; j++)
                if (bands[i].getStartTime().compareTo(bands[j].getStartTime())>0) {
                    Band tmp = bands[i];
                    bands[i] = bands[j];
                    bands[j] = tmp;
                }
	}
	private boolean validateBandsInDay(Band[] bandsInDay) {
		boolean c1=(bandsInDay[0].getStartTime().equals(LocalTime.MIN));
		boolean c2=(bandsInDay[bandsInDay.length-1].getEndTime().equals(LocalTime.MAX));
		boolean c3=true;
		boolean c4=(bandsInDay.length>0);
		for(int i=0; i<bandsInDay.length-1 && c3; i++) {
			if(!bandsInDay[i+1].getStartTime().equals(bandsInDay[i].getEndTime().plusNanos(1)))
				c3=false;
		}
		return c1 && c2 && c3 && c4;
	}
	private boolean validateDay(DayOfWeek day) {
		if(day!=null) {
			Band[] newBands=selectBandsInDay(day);
			sortBandsByStartTime(newBands);
			return validateBandsInDay(newBands);
		}
		else
			return false;
	}
	private double getCostPerInterval(LocalDateTime dateTime) {
		double result=-1;
		for(Band b: bands) {
			if(b.isInBand(dateTime)) {
				result=b.getCostPerInterval();
			}	
		}
		return result;
	}
	public double getCallCost(PhoneCall call) {
		Duration duration=Duration.between(call.getStart(), call.getEnd());
		long durationM=duration.toMillis();
		int intervalli= (int) Math.ceil(((double)durationM)/this.intervalMillis);
		return this.getStartCallCost()+intervalli*getCostPerInterval(call.getStart());
	}
	public boolean isValid() {
		for(Band b: bands) {
			if(!b.isValid())
				return false;
		}
		for(DayOfWeek day: DayOfWeek.values()) {
			if(!validateDay(day))
				return false;
		}
		return true;
	}
}



































