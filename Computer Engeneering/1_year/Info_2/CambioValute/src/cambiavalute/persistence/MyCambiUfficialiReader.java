package cambiavalute.persistence;

import java.io.BufferedReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import cambiavalute.model.TassoDiCambioUfficiale;

public class MyCambiUfficialiReader implements CambiUfficialiReader {

	@Override
	public Map<String, TassoDiCambioUfficiale> leggiTabellaCambiUfficiali(Reader reader) throws BadFileFormatException {
		BufferedReader br=new BufferedReader(reader);
		String line;
		Map<String, TassoDiCambioUfficiale> result=new TreeMap<>();
		try {
			while((line=br.readLine())!=null) {
				String[] splittati=line.split(",");
				if(splittati.length<4) throw new BadFileFormatException("formato errato");
				
				String paese=splittati[0].trim();
				if(paese.isEmpty() || paese.isBlank()) throw new BadFileFormatException("paese");
				
				String valuta=splittati[1].trim();
				if(valuta.isBlank() || valuta.isEmpty()) throw new BadFileFormatException("valuta");
				
				String siglaValuta=splittati[2].trim();
				if(siglaValuta.isBlank() || siglaValuta.isEmpty()) throw new BadFileFormatException("sigla valuta");
				
				Number tasso= NumberFormat.getNumberInstance(Locale.ENGLISH).parse(splittati[4].trim());
				if(tasso==null) throw new BadFileFormatException("tasso cambio");
				double tassoCambio=tasso.doubleValue();
				
				result.put(siglaValuta, new TassoDiCambioUfficiale(siglaValuta, valuta, paese, tassoCambio));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}

}
