package edicecream.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import edicecream.model.IceCreamKind;

public class MyIceCreamKindsRepository implements IceCreamKindsRepository{
	private TreeMap<String, IceCreamKind> map;
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ITALY);
	
	private TreeMap<String, IceCreamKind> leggi(BufferedReader reader) throws IOException, BadFileFormatException{
		var result= new TreeMap<String, IceCreamKind>();
		String line;
			while((line=reader.readLine())!=null) {
				line=line.trim();
				if(line.length()==0)continue;
				
				String[] splittati=line.split("-");
				if(splittati.length!=4) throw new BadFileFormatException("Campi non esatti");
				
				String NomeTipologia=splittati[0].trim();
				if(NomeTipologia.length()==0) throw new BadFileFormatException("Nome vuoto");
				
				Number prezzo;
				int NumGusti;
				int Peso;
				
				try {
					String value=splittati[1].trim();
					prezzo=NUMBER_FORMAT.parse(value);
					
					NumGusti= Integer.parseInt(splittati[2].trim());
					
					Peso=Integer.parseInt(splittati[3].trim());
				} 
				catch (Exception e) {
					throw new BadFileFormatException("Formato file errato", e);
				} 
				
				result.put(NomeTipologia, new IceCreamKind(NomeTipologia, prezzo.floatValue(), NumGusti, Peso));
		}
		return result;
	}

	public MyIceCreamKindsRepository(Reader baseReader) throws IOException, BadFileFormatException {
		if(baseReader==null) {
			throw new IllegalArgumentException();
		}
		this.map=this.leggi(new BufferedReader(baseReader));
	}
	
	@Override
	public Set<String> getKindNames() {
		return this.map.keySet();
	}

	@Override
	public IceCreamKind getIceCreamKind(String kind) {
		return this.map.get(kind);
	}

}
