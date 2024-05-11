package gasforlife.model;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Bill {
	private BillingFrequency billFreq;
	private double comsumption;
	private double costm3;
	private double extraCostm3;
	private double fixedCost;
	private Optional<Month> month;
	private List<Share> quotes;
	private double value;
	private double variableCost;
	
	
	
	public Bill(BillingFrequency billFreq, double total, double fixedCost, double variableCost, double comsumption, double costm3, double extraCostm3, Optional<Month> month) {
		super();
		if(billFreq==null || total<=0 || fixedCost<=0 || variableCost<=0 || comsumption<=0 || costm3<=0 || extraCostm3<=0 || month==null) {
			throw new IllegalArgumentException("paramentri in ingresso nulli o negativi");
		}
		this.billFreq = billFreq;
		this.value=total;
		this.fixedCost = fixedCost;
		this.variableCost = variableCost;
		this.comsumption = comsumption;
		this.costm3 = costm3;
		this.extraCostm3 = extraCostm3;
		this.month = month;
		this.quotes=new ArrayList<Share>();
		//ordinamento
		Collections.sort(this.quotes, (s1,s2) -> s2.getFlat().getId().compareTo(s1.getFlat().getId()) );
		
	}
	public BillingFrequency getBillingFrequency() {
		return billFreq;
	}
	public double getConsumption() {
		return comsumption;
	}
	public double getCostm3() {
		return costm3;
	}
	public double getExtraCostm3() {
		return extraCostm3;
	}
	public double getFixedCost() {
		return fixedCost;
	}
	public Optional<Month> getMonth() {
		return month;
	}
	public List<Share> getShares() {
		return quotes;
	}
	public double getValue() {
		return value;
	}
	public double getVariableCost() {
		return variableCost;
	}
	public String getMonthAsString() {
		if(this.month.isPresent()) {
			return DateTimeFormatter.ofPattern("MMMM").format(this.month.get());
		}
		else
			return "mese non presente";
	}
	public boolean addShare(Share quote) {
		this.quotes.add(quote);
		if(this.quotes.contains(quote)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Frequenza: "+this.billFreq+"\n");
		sb.append("Totale: "+this.value+"\n");
		sb.append("FixedCost: "+this.fixedCost+"\n");
		sb.append("VariableCost: "+this.variableCost+"\n");
		sb.append("Comsumption: "+this.comsumption+"\n");
		sb.append("costm3: "+this.costm3+"\n");
		sb.append("extraCostm3: "+this.extraCostm3+"\n");
		sb.append("mese: "+this.getMonthAsString()+"\n");
		for(Share s: this.quotes) {
			sb.append( s.toString()+"\n");
		}
		return sb.toString();
	}
}
