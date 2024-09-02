package unident.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import unident.model.AttivitaFormativa;
import unident.model.Ssd;
import unident.model.Tipologia;

public class UniDentPianoDidatticoReader implements PianoDidatticoReader{

	@Override
	public Set<AttivitaFormativa> readAll(Reader rdr) throws IOException {
		Set<AttivitaFormativa> result=new TreeSet<>();
		BufferedReader br=new BufferedReader(rdr);
		String line;
		try {
			while((line=br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line, "//t");
//				String[] splittati=line.trim().split("//t+");
				if(st.countTokens()!=6 && st.countTokens()!=5) {
					throw new BadFileFormatException("campi mancanti");
				}
				
				String codice=st.nextToken().trim();//da ignorare
				if(codice.isEmpty()) {
					throw new BadFileFormatException("codice mancante");
				}
				
				String nome=st.nextToken().trim();
				if(nome.isEmpty()) {
					throw new BadFileFormatException("nome mancante");
				}
				
				String periodo=st.nextToken().trim();//da ignorare
				if(periodo.isEmpty()) {
					throw new BadFileFormatException("periodo mancante");
				}
				
				String tipo=st.nextToken().trim();
				Tipologia tipologia;
				try {
					tipologia=Tipologia.valueOf(tipo);
				}
				catch(IllegalArgumentException e) {
					throw new BadFileFormatException("tipologia errata");
				}
				
				Ssd settore;
				String crediti=st.nextToken().trim();
				if(tipologia.equals(Tipologia.A) || tipologia.equals(Tipologia.B) || tipologia.equals(Tipologia.C) ||
						tipologia.equals(Tipologia.D)) {
						settore=Ssd.SENZASETTORE;
					
				}
				else {
					try {
						settore=Ssd.of(st.nextToken().trim());
						int cfu=Integer.parseInt(crediti);
					}
					catch(Exception e) {
						
					}
				}
				
				result.add(new AttivitaFormativa(crediti, tipologia, settore, cfu));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
		
		
	}

}
