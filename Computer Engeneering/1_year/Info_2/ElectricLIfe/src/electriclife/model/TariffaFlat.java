package electriclife.model;

import java.time.LocalDate;

public class TariffaFlat extends Tariffa{
	private double prezzoKWhExtra;
	private double sogliaFissaMensile;
	private int sogliaMensile;

	public double getPrezzoKWhExtra() {
		return prezzoKWhExtra;
	}

	public double getQuotaFissaMensile() {
		return sogliaFissaMensile;
	}

	public int getSogliaMensile() {
		return sogliaMensile;
	}

	public TariffaFlat(String nome, double sogliaFissaMensile, int sogliaMensile, double prezzoKWhExtra) {
		super(nome);
		this.sogliaFissaMensile=sogliaFissaMensile;
		this.sogliaMensile=sogliaMensile;
		this.prezzoKWhExtra=prezzoKWhExtra;
	}

	@Override
	public Bolletta creaBolletta(LocalDate date, int consumo) {
		Bolletta result=new Bolletta(date, this, consumo);
		
		double quotaFissaMensile=this.sogliaFissaMensile;
		double costoEnergiaExtra=consumo<this.sogliaMensile? 0: this.prezzoKWhExtra*(consumo-this.sogliaMensile);
		double accise=this.calcAccise(consumo);
		double iva=this.calcIVA(quotaFissaMensile+costoEnergiaExtra+accise);
		
		result.addLineaBolletta("quota fissa mensile", quotaFissaMensile);
		result.addLineaBolletta("costo energia extra soglia", costoEnergiaExtra);
		result.addLineaBolletta("accise", accise);
		result.addLineaBolletta("IVA", iva);
		result.addLineaBolletta("totale", quotaFissaMensile+costoEnergiaExtra+accise+iva);
		
		return result;
	}

	@Override
	public String getDettagli() {
		return "Tariffa CASETTA, € "+this.sogliaFissaMensile+"/mese per "+this.sogliaMensile+" KWh, poi € "+this.prezzoKWhExtra+"/KWh";
	}

}
