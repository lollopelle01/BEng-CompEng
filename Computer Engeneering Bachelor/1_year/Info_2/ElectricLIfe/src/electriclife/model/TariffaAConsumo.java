package electriclife.model;

import java.time.LocalDate;

public class TariffaAConsumo extends Tariffa{
	private double prezzoKWh;

	public TariffaAConsumo(String nome, double prezzoKWh) {
		super(nome);
		this.prezzoKWh=prezzoKWh;
	}

	public double getPrezzoKWh() {
		return prezzoKWh;
	}
	
	@Override
	public Bolletta creaBolletta(LocalDate date, int consumo) {
		Bolletta result=new Bolletta(date, this, consumo);
		
		double costoEnergia=consumo*this.prezzoKWh;
		double accise=this.calcAccise(consumo);
		double iva=this.calcIVA(costoEnergia+accise);
		
		result.addLineaBolletta("costo energia", costoEnergia);
		result.addLineaBolletta("accise", accise);
		result.addLineaBolletta("IVA", iva);
		result.addLineaBolletta("totale", costoEnergia+accise+iva);
		
		return result;
	}

	@Override
	public String getDettagli() {
		return "Tariffa A CONSUMO, Costo KWh € "+this.prezzoKWh;
	}

}
