package rfd.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import rfd.model.RailwayLine;
import rfd.model.Station;

public class MyRailwayLineReader implements RailwayLineReader{
private NumberFormat formattatore=NumberFormat.getInstance(Locale.ITALY);
	@Override
	public RailwayLine getRailwayLine(Reader rdr) throws IOException {
		BufferedReader br=new BufferedReader(rdr);
		String line;
		Map<String, Station> mappa=new TreeMap<>();
		SortedSet<String> set=new TreeSet<>();
		
		while((line=br.readLine())!=null) {
			String prog=line.substring(0, 8).trim();
			double progressione;
			try {
				progressione=this.formattatore.parse(prog).doubleValue();
			}
			catch(ParseException e) {
				throw new IllegalArgumentException("progressione");
			}
			
			String nome=line.substring(8, line.length()-3).trim();
			if(nome.isEmpty() || nome.equals("HUB"))
				throw new IllegalArgumentException("nome");
			if(nome.contains("HUB")) {
				set.add(nome);
			}
			
			String v=line.substring(line.length()-3, line.length()).trim();
			if(v.isEmpty())
				throw new IllegalArgumentException("velocità");
			int velocita=Integer.parseInt(v);
			
			mappa.put(nome, new Station(v, progressione, velocita));
		}
		
		return new RailwayLine(mappa, set);
	}

}
