package cupidonline.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.time.format.DateTimeParseException;

import cupidonline.model.Colore;
import cupidonline.model.Persona;
import cupidonline.model.SegnoZodiacale;
import cupidonline.model.Sesso;

public class MyIscrittiReader implements IscrittiReader{

	@Override
	public Map<String, Persona> caricaIscritti(Reader reader) throws IOException, BadFileFormatException {
		Map<String, Persona> result=new TreeMap<String, Persona>();
		String line;
		try {
			BufferedReader br=new BufferedReader(reader);
			while((line=br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line, ",");
				Persona p=this.estraiPersona(st);
				result.put(p.getId(), p);
			}
			br.close();
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
		return result;
	}
	
	private Persona estraiPersona(StringTokenizer st) throws BadFileFormatException {
		try {
			String id= st.nextToken(",").trim();
			if(id.isEmpty() || id.isBlank()) throw new BadFileFormatException("nome");
			
			Sesso sex=Sesso.valueOfChar(st.nextToken(",").trim());
			if(sex==null)  throw new BadFileFormatException("sesso");
			
			LocalDate nascita=LocalDate.parse(st.nextToken(",").trim(), DateTimeFormatter.ISO_LOCAL_DATE);
			if(nascita==null) throw new BadFileFormatException("nascita");
			
			String[] chioma = st.nextToken(",").trim().split(" ");
			if (!chioma[0].equalsIgnoreCase("CAPELLI")) throw new BadFileFormatException("manca indicazione CAPELLI");
			Colore capelli = Colore.valueOf(chioma[1].toUpperCase());
			if(capelli==null) throw new BadFileFormatException("capelli");

			String[] occhio = st.nextToken(",").trim().split(" ");
			if (!occhio[0].equalsIgnoreCase("OCCHI")) throw new BadFileFormatException("manca indicazione OCCHI");
			Colore occhioC = Colore.valueOf(occhio[1].toUpperCase());
			if(occhioC==null) throw new BadFileFormatException("occhi");
			
			Float altezza=Float.valueOf(st.nextToken(",").trim());
			if(altezza==null) throw new BadFileFormatException("altezza");
			
			Integer peso=Integer.valueOf(st.nextToken(",").trim());
			if(peso==null) throw new BadFileFormatException("peso");
			
			String citta=st.nextToken(",").trim();
			if(citta.isBlank() || citta.isEmpty()) throw new BadFileFormatException("città");
			
			String provincia= st.nextToken(",").trim();
			if(provincia.isBlank() || provincia.isEmpty()) throw new BadFileFormatException("provincia");
			
			st.nextToken("qwertyuiopasdfghjklzxcvbnmZXCVBNMASDFGHJKLQWERTYUIOP"); //escludo la virgola
			
			String regione=st.nextToken("\n\t").trim();
			if(regione.isBlank() || regione.isEmpty()) throw new BadFileFormatException("regione");
			
			return new Persona(id, sex, nascita, this.calcolaSegnoZodiacale(nascita), capelli, occhioC, altezza, peso, citta, provincia, regione);
			
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
	
	private SegnoZodiacale calcolaSegnoZodiacale(LocalDate d) throws BadFileFormatException {
		for(SegnoZodiacale s: SegnoZodiacale.values()) {
			if(s.contains(d)) {
				return s;
			}
		}
		throw new BadFileFormatException("Oroscopo");
	}

}
