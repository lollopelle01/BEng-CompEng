package flightTracker.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import flightTracker.model.Flight;
import flightTracker.model.FlightPos;

public class MyFlightReader implements FlightReader{

	
	private void validate(String s) throws BadFileFormatException {
		String[] splittati=s.trim().split(";");
		if(splittati.length!=5) {
			throw new BadFileFormatException("Campi mancanti");
		}
		if(!splittati[0].equals("UTC") || !splittati[1].equals("Position") || !splittati[2].equals("Altitude") 
				|| !splittati[3].equals("Speed") || !splittati[4].equals("Direction")) {
			throw new BadFileFormatException("Campi errati");
		}
	}
	@Override
	public Flight readFlight(String id, BufferedReader reader) throws IOException, BadFileFormatException {
		List<FlightPos> posizioni=new ArrayList<>();
		String line;
		try {
			line=reader.readLine();
			this.validate(line);
			while((line=reader.readLine())!=null) {
				String[] splittati=line.trim().split(";");
				if(splittati.length!=5) {
					throw new BadFileFormatException("Campi mancanti");
				}
				if(splittati[0].isEmpty() || splittati[1].isEmpty() || splittati[2].isEmpty() 
						|| splittati[3].isEmpty() || splittati[4].isEmpty()) {
					throw new BadFileFormatException("Campi errati");
				}
				ZonedDateTime timeStamp=ZonedDateTime.parse(splittati[0]);
				
				String[] splittati2=splittati[1].trim().split(",");
				if(splittati2.length!=2) {
					throw new BadFileFormatException("Campi mancanti");
				}
				if(splittati2[0].trim().isEmpty() || splittati2[1].trim().isEmpty()) {
					throw new BadFileFormatException("Campi errati");
				}
				double latitudine=Double.parseDouble(splittati2[0]);
				
				double longitudine=Double.parseDouble(splittati2[1]);
				
				double altitudine=Double.parseDouble(splittati[2]);
				
				double speed=Double.parseDouble(splittati[3]);
				
				int direzione=Integer.parseInt(splittati[4]);
				
				posizioni.add(new FlightPos(timeStamp, latitudine, longitudine, altitudine, speed, direzione));
			}
			return new Flight(id, posizioni);
		}
		catch(Exception e) {
			throw new BadFileFormatException();
		}
	}

}
