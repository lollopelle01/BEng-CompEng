package cambiavalute.ui.controller;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;

import cambiavalute.model.CambiaValute;
import cambiavalute.model.TassoDiCambioUfficiale;

public class MyController extends Controller{
	
	public MyController(UserInteractor userInteractor, Map<String, TassoDiCambioUfficiale> cambiUfficiali,
			List<CambiaValute> listaCambiaValute) {
		super(userInteractor, cambiUfficiali, listaCambiaValute);
	}

	@Override
	public Map<String, OptionalDouble> changeTo(String siglaValutaEstera, double importo) {
		Map<String,OptionalDouble> result=new TreeMap<>();
		for(CambiaValute c: this.getListaCambiaValute()) {
			result.put(c.getNomeAgenzia(), c.vendita(siglaValutaEstera, importo));
		}
		return result;
	}

	@Override
	public Map<String, OptionalDouble> changeFrom(String siglaValutaEstera, double importo) {
		Map<String,OptionalDouble> result=new TreeMap<>();
		for(CambiaValute c: this.getListaCambiaValute()) {
			result.put(c.getNomeAgenzia(), c.acquisto(siglaValutaEstera, importo));
		}
		return result;
	}

}
