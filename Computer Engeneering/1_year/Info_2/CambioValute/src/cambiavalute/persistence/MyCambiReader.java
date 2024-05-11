package cambiavalute.persistence;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import cambiavalute.model.CambiaValute;
import cambiavalute.model.TassiDiCambio;

public class MyCambiReader implements CambiReader{

	@Override
	public Map<String, TassiDiCambio> leggiTabellaCambiApplicati(Reader reader) throws BadFileFormatException {
		Map<String, TassiDiCambio> result= new TreeMap<>();
		BufferedReader br=new BufferedReader(reader);
		String line=null;
		try {
			while((line=br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line);
				if(st.countTokens()!=3) throw new BadFileFormatException("campi non conformi");
				
				String sigla=st.nextToken().trim();
				if(sigla.isEmpty() || sigla.isBlank()) throw new BadFileFormatException("sigla");
				
				double venditaP=CambiaValute.convertiInDouble(st.nextToken().trim());
				
				double acquistoP=CambiaValute.convertiInDouble(st.nextToken().trim());
				
				result.put(sigla, new TassiDiCambio(sigla, acquistoP, venditaP));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
}
