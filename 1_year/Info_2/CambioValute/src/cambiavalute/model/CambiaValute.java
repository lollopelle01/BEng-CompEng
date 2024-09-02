package cambiavalute.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;

public class CambiaValute {
	private String nomeAgenzia;
	private Map<String, TassiDiCambio> elencoValute;
	
	public String getNomeAgenzia() {
		return nomeAgenzia;
	}
	public Map<String, TassiDiCambio> getElencoValute() {
		return elencoValute;
	}
	public CambiaValute(String nomeAgenzia, Map<String, TassiDiCambio> elencoValute) {
		super();
		this.nomeAgenzia = nomeAgenzia;
		this.elencoValute = elencoValute;
	}
	
	@Override
	public String toString() {
		return this.nomeAgenzia;
	}
	
	public static double arrotonda(double importo) {
		return Math.round(100.0*importo)/100.0;
	}
	
	public static String formatta(double importo) {
		NumberFormat f=NumberFormat.getNumberInstance(Locale.ITALY);
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		return f.format(importo);
	}
	
	public static double convertiInDouble(String importo) throws ParseException {
		if(importo.contains(".")) {
			throw new ParseException(".", importo.length()-2);
		}
		Number result=NumberFormat.getInstance(Locale.ITALY).parse(importo);
		return result.doubleValue();
	}
	
	public OptionalDouble vendita(String siglaValutaEstera, double importo) {
		for(String valuta: this.elencoValute.keySet()) {
			if(valuta.equals(siglaValutaEstera)) {
				return OptionalDouble.of(CambiaValute.arrotonda(this.elencoValute.get(valuta).getPrezzoVendita()*importo));
			}
		}
		return OptionalDouble.empty();
	}
	
	public OptionalDouble acquisto(String siglaValutaEstera, double importo) {
		for(String valuta: this.elencoValute.keySet()) {
			if(valuta.equals(siglaValutaEstera)) {
				return OptionalDouble.of(CambiaValute.arrotonda(importo/this.elencoValute.get(valuta).getPrezzoAcquisto()));
			}
		}
		return OptionalDouble.empty();
	}
	
	
	
}
