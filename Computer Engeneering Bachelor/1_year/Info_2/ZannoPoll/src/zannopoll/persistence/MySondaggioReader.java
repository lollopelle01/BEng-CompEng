package zannopoll.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import zannopoll.model.Intervista;
import zannopoll.model.Sesso;

public class MySondaggioReader implements SondaggioReader{

	@Override
	public List<Intervista> leggiRisposte(Reader r) throws IOException, BadFileFormatException {
		if(r==null) {
			throw new IllegalArgumentException();
		}
		
		List<Intervista> result=new ArrayList<>();
		String line;
		BufferedReader br=new BufferedReader(r);
		try {
			while((line=br.readLine())!=null) {
				String[] splittati=line.trim().split(",");
				if(splittati.length!=3) throw new BadFileFormatException("campi mancanti");
				if(splittati[0].trim().isEmpty() || splittati[1].trim().isEmpty() || splittati[2].trim().isEmpty()) throw new BadFileFormatException("campi mancanti");
				
				String risposta=splittati[0].trim();
				int age=Integer.parseInt(splittati[1].trim());
				String sex=splittati[2].trim();
				Sesso sesso;
				if(sex.equals("M")) sesso=Sesso.MASCHIO;
				else if(sex.equals("F")) sesso=Sesso.FEMMINA;
				else throw new BadFileFormatException("sesso errato");
				
				result.add(new Intervista(risposta, age, sesso));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
		
	}

}
