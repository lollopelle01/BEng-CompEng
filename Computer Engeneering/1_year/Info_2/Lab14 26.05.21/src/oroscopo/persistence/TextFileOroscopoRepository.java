package oroscopo.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class TextFileOroscopoRepository implements OroscopoRepository{
	private Map<String, List<Previsione>> data;
	
	private List<Previsione> caricaPrevisione(BufferedReader reader) throws IOException, BadFileFormatException{
		List<Previsione> result = new ArrayList<Previsione>();
		
		try {
			String riga =null;
			while(!(riga=reader.readLine()).trim().equalsIgnoreCase("FINE")) {
				if(riga.isEmpty()) {
					continue;
				}
				
				StringTokenizer st=new StringTokenizer(riga, "\t\n\r");
				String frase=st.nextToken("\t").trim();
				int fortuna=Integer.valueOf(st.nextToken().trim());
				Previsione p=null;
				
				if(st.hasMoreTokens()) {
					String segniEventuali=st.nextToken();
					
					try {
						Set<SegnoZodiacale> segni=new TreeSet<SegnoZodiacale>();
						String[] Segni= segniEventuali.split(",");
						for(String s: Segni) {
							segni.add(SegnoZodiacale.valueOf(s.trim().toUpperCase()));
						}
						p=new Previsione(frase, fortuna, segni);
					}
					catch(Exception e) {
						throw new BadFileFormatException("lista con valori errati");
					}
				}
				else {
					p=new Previsione(frase, fortuna);
				}
				result.add(p);
			}
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
		if(result.size()==0) {
			throw new BadFileFormatException("lista vuota");
		}
		return result;
	}
	
	public TextFileOroscopoRepository(Reader baseReader) throws IllegalArgumentException, BadFileFormatException, IOException{
		
		if(baseReader==null) {
			throw new IllegalArgumentException("Reader nullo");
		}
		this.data=new HashMap<String, List<Previsione>>();
		BufferedReader br=new BufferedReader(baseReader);
		String riga=null;
		while((riga =br.readLine())!=null) {
			if(riga.contains(" ") || riga.contains("\t")) {
				throw new BadFileFormatException("riga errata");
			}
			this.data.put(riga, this.caricaPrevisione(br));
		}
	}
	
	@Override
	public Set<String> getSettori() {
		return this.data.keySet();
	}

	@Override
	public List<Previsione> getPrevisioni(String settore) {
		return this.data.get(settore.trim().toUpperCase());
	}

}
