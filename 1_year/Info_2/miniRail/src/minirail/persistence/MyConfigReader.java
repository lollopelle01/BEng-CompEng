package minirail.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import minirail.model.Gauge;
import minirail.model.Train;

public class MyConfigReader implements ConfigReader{
	private Gauge gauge;
	private List<Train> trains;
	
	@Override
	public List<Train> getTrains() {
		return this.trains;
	}

	@Override
	public Gauge getGauge() {
		return this.gauge;
	}
	
	public MyConfigReader(Reader reader) throws IOException, BadFileFormatException {
		BufferedReader br=new BufferedReader(reader);
		String line;
		this.trains=new ArrayList<>();
		try {
			if((line=br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line, "g");
				if(st.countTokens()!=3)
					throw new BadFileFormatException("Campi mancanti scala");
				
				String scala=st.nextToken().trim();
				if(scala.isEmpty()) throw new BadFileFormatException("scala mancante");
				
				
				this.gauge=Gauge.valueOf(scala);//calcola scala
				if(!st.nextToken("\n").equalsIgnoreCase("gauge"))  throw new BadFileFormatException("gauge mancante");
				
				while((line=br.readLine())!=null) {
					String[] splittati=line.trim().split(",");
					if(splittati.length!=3)
						throw new BadFileFormatException("Campi mancanti treni");
					
					String nome=splittati[0].trim();
					
					String[] splittati1=splittati[1].trim().split(" ");
					if(splittati1.length!=2)
						throw new BadFileFormatException("Campi lunghezza mancanti");
					if(!splittati1[1].equals("cm") && !splittati1[1].equals("in")) {
						throw new BadFileFormatException("Unita misura errata");
					}
					double lunghezza=Double.parseDouble(splittati1[0]);
					
					String[] splittati2=splittati[2].trim().split(" ");
					if(splittati2.length!=2)
						throw new BadFileFormatException("Campi velocita mancanti");
					if(!splittati2[1].equals("km/h") && !splittati2[1].equals("mph")) {
						throw new BadFileFormatException("Unita misura errata");
					}
					double velocita=Double.parseDouble(splittati2[0]);
					
					this.trains.add(new Train(nome, lunghezza, velocita));
				}
				
			}
			else
				throw new BadFileFormatException("linea nulla");
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
		
		
		
		
	}

}
