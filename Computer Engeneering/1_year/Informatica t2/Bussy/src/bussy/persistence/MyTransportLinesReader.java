package bussy.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaCircolare;
import bussy.model.LineaPaP;

public class MyTransportLinesReader implements TransportLinesReader{

	@Override
	public Map<String, Linea> readTransportLines(BufferedReader reader) throws IOException, BadFileFormatException {
		Map<String, Linea> result=new TreeMap<>();
		String line;
		try {
			while((line=reader.readLine())!=null) {
				String[] splittati=line.split(" ");
				if(splittati.length!=2) throw new BadFileFormatException("Campi mancanti");
				if(!splittati[0].equals("Linea")) throw new BadFileFormatException("Campi errati");
				
				String id=splittati[1].trim();
				
				Map<Integer, Fermata> fermate=this.readTransportLine(reader);
				int tempoFinale=Collections.max(fermate.keySet());
		
				if(fermate.get(0).getNome().equals(fermate.get(tempoFinale).getNome())) {
					result.put(id, new LineaCircolare(id, fermate));
				}
				else
					result.put(id, new LineaPaP(id, fermate));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException();
		}
	}
	
	private Map<Integer, Fermata> readTransportLine(BufferedReader reader) throws IOException, BadFileFormatException {
		String line;
		Map<Integer, Fermata> fermate=new TreeMap<>();
		while(!(line=reader.readLine()).contains("--")) {
			String[] splittati=line.split(",");
			if(splittati.length!=3) throw new BadFileFormatException("Campi mancanti");
			if(splittati[2].trim().isEmpty()) throw new BadFileFormatException("Campi errati");
			
			int orario=Integer.parseInt(splittati[0].trim());
			
			String id=splittati[1].trim();
			
			String denominazione=splittati[2].trim();
			
			fermate.put(orario, new Fermata(id, denominazione));
		}
		return fermate;
	}

}
