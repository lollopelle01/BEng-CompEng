package gasforlife.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.net.httpserver.Authenticator.Result;

public class MyConsumptionReader implements ConsumptionReader{
	private final double Fattore_conversione=10.69;
	private Map<String, List<Double>> gasConsumption;
	
	public MyConsumptionReader(Reader r) throws BadFileFormatException, IOException {
		this.loadAllItems(r);
	}
	@Override
	public Map<String, List<Double>> getItems() {
		return this.gasConsumption;
	}
	
	public void loadAllItems(Reader r) throws BadFileFormatException, IOException {
		this.gasConsumption=new TreeMap<>();
		BufferedReader br=new BufferedReader(r);
		String line;
		try {
			while((line=br.readLine())!=null) {
				List<Double> valoriMensili=new ArrayList<Double>();
				String[] splittati=line.trim().split(":");
				if(splittati.length!=2) {
					throw new BadFileFormatException("campi mancanti");
				}
				
				String codice=splittati[0].trim();
				if(codice.isEmpty()) {
					throw new BadFileFormatException("codice mancante");
				}
				//
				String[] splittati2=splittati[1].trim().split("\\|");
				if(splittati2.length!=12) {
					throw new BadFileFormatException("campi mancanti nei mesi");
				}
				
				for(int i=0; i<12; i++) {
					if(splittati2[i].isEmpty()) {
						throw new BadFileFormatException("manca il mese numero" +i);
					}
					else {
						try {
							valoriMensili.add(Double.parseDouble(splittati2[i].trim())/this.Fattore_conversione);

						}
						catch(NumberFormatException e) {
							throw new BadFileFormatException("formato errato");
						}
					}
				}
				this.gasConsumption.put(codice, valoriMensili);
			}
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}

}
