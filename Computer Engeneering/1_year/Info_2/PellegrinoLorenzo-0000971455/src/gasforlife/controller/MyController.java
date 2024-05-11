package gasforlife.controller;

import java.time.Month;
import java.util.List;
import java.util.Map;

import gasforlife.model.Bill;
import gasforlife.model.Flat;
import gasforlife.model.Share;
import gasforlife.model.BillingFrequency;
import gasforlife.persistence.ConsumptionReader;
import gasforlife.persistence.FlatReader;

public class MyController implements Controller {
	private Map<String, List<Double>> gasConsumption;
	private Map<String, Flat> flats;

	public MyController(FlatReader flatReader, ConsumptionReader conReader) {
		this.gasConsumption = conReader.getItems();
		this.flats = flatReader.getItems();
	}

	@Override
	public void computeShare(Bill bill) {
		switch(bill.getBillingFrequency()) {
			case ANNUAL:  computeAnnualCost(bill); break;
			case MONTHLY: computeMonthlyCost(bill); break;
		}
	}

	private void computeAnnualCost(Bill bill) {
		double totalPrice = 0;
		for (String flat : flats.keySet()) {
			double price = 0;
			double totalCons = 0;
			for (int month = 0; month < 12; month++) {
				double realCons = gasConsumption.get(flat).get(month);
				price += this.getMonthlyCostForFlat(flats.get(flat), bill, realCons);
				totalCons += realCons;

			}
			totalPrice += price;
			price += bill.getFixedCost() / flats.size();
			bill.addShare(new Share(flats.get(flat), totalCons, price));
		}
		updateShare(bill, totalPrice);
	}

	private void updateShare(Bill bill, double totalPrice) {
		double update = (bill.getVariableCost() - totalPrice) / flats.size();
		for (Share q : bill.getShares()) {
			q.addCorrection(update);
		}

	}

	// ------------ PRIMO METODO DA REALIZZARE------------------
	private void computeMonthlyCost(Bill bill) {
		if(bill.getMonth().isPresent()) {
			double totalPrice = 0;
			for (String flat : flats.keySet()) {
				double price = 0;
				double totalCons = 0;
				for (int month = 0; month < 12; month++) {
					if(Month.of(month+1).equals(bill.getMonth().get())) {
						double realCons = gasConsumption.get(flat).get(month);
						price += this.getMonthlyCostForFlat(flats.get(flat), bill, realCons);
						totalCons += realCons;
					}
				}
				totalPrice += price;
				price += bill.getFixedCost() / flats.size();
				bill.addShare(new Share(flats.get(flat), totalCons, price));
			}
			updateShare(bill, totalPrice);
		}
		else {
			throw new IllegalArgumentException("mese non presente");
		}
		
	}
	
	private double getMonthlyCostForFlat(Flat flat, Bill bill, double realCons) {
		double result=0;
		double consMax=flat.getMaxConsumption();
		if(realCons>consMax) {
			result=(realCons-consMax)*bill.getExtraCostm3()+consMax*bill.getCostm3();
		}
		else
			result=realCons*bill.getCostm3();
		return result;
	}
	// ------------ FINE METODO DA REALIZZARE-------------------

	@Override
	public double getMonthlyTotalConsumption(int index) {
		double total = 0;
		for (String flat : flats.keySet()) {
			total += gasConsumption.get(flat).get(index);
		}
		return total;
	}

	@Override
	public double getAnnualTotalConsumption() {
		double total = 0;

		for (int i = 0; i < 12; i++) {
			total += getMonthlyTotalConsumption(i);
		}

		return total;
	}

	// ------------ SECONDO METODO DA REALIZZARE------------------
	@Override
	public double getDiffCons(Bill bill) {
		double result=0;
		if(bill.getBillingFrequency().equals(BillingFrequency.ANNUAL)) {
			result=this.getAnnualTotalConsumption()-bill.getConsumption();
		}
		else {
			for(int i=1; i<13; i++) {
				if(Month.of(i).equals(bill.getMonth().get())) {
					result=this.getMonthlyTotalConsumption(i)-bill.getConsumption();
				}
			}
		}
		return -result;
	}
	// ------------ FINE METODO DA REALIZZARE-------------------

}
