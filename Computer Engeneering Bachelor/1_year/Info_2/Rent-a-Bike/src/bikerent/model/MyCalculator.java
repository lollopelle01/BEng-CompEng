package bikerent.model;

import java.time.Duration;

public class MyCalculator implements Calculator{

	@Override
	public double calc(Rate rate, Rent rent) {
		double result=0;
		Duration durataNoleggio=Duration.between(rent.getStart(), rent.getEnd());
		
		if(durataNoleggio.compareTo(rate.getDurataPrimoPeriodo())>0) {
			durataNoleggio=durataNoleggio.minus(rate.getDurataPrimoPeriodo());
			while(!durataNoleggio.isNegative()) {
				durataNoleggio=durataNoleggio.minus(rate.getDurataPeriodiSuccessivi());
				result=result+rate.getCostoPeriodiSuccessivi();
			}
			result=result+rate.getCostoPrimoPeriodo();
		}
		else {
			result=rate.getCostoPrimoPeriodo();
			
		}
	
		if(!rent.isRegular()) {
			result=result/100+rate.getSanzione();
		}
		else
			result=result/100;
		
		return result;
	}

}
