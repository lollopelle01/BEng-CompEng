package flights.persistence;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.City;
import flights.model.FlightSchedule;

public class DataManager {
	private HashMap<String, Aircraft> aircraftMap;
	private AircraftsReader aircraftsReader;
	private HashMap<String, Airport> airportMap;
	private CitiesReader citiesReader;
	private FlightScheduleReader flightScheduleReader;
	private Collection<FlightSchedule> flightSchedules;
	
	public Map<String, Aircraft> getAircraftMap() {
		Map<String, Aircraft> mappa = new TreeMap<String, Aircraft>(this.aircraftMap);
		return mappa;
	}
	public Map<String, Airport> getAirportMap() {
		Map<String, Airport> mappa = new TreeMap<String, Airport>(this.airportMap);
		return mappa;
	}
	public Collection<FlightSchedule> getFlightSchedules() {
		return flightSchedules;
	}
	public DataManager(CitiesReader citiesReader, AircraftsReader aircraftsReader,
			FlightScheduleReader flightScheduleReader) {
		super();
		this.citiesReader = citiesReader;
		this.aircraftsReader = aircraftsReader;
		this.flightScheduleReader = flightScheduleReader;
	}
	
	public void readAll() throws IOException, BadFileFormatException {
		try {
			var reader1=new FileReader("Cities.txt");
			var reader2=new FileReader("Aircrafts.txt");
			var reader3=new FileReader("FlightSchedule.txt");
			
			Collection<Aircraft> aircrafts=this.aircraftsReader.read(reader2);
			this.aircraftMap.clear();
			Collection<City> cities=this.citiesReader.read(reader1);
			this.airportMap.clear();
			Collection<FlightSchedule> schedule= this.flightScheduleReader.read(reader3, airportMap, aircraftMap);
			this.flightSchedules.clear();
			
			for(City c: cities) {
				for(Airport a: c.getAirports()) {
					this.airportMap.put(a.getCode(), a);
				}
			}
			for(Aircraft a: aircrafts) {
				this.aircraftMap.put(a.getCode(), a);
			}
			for(FlightSchedule f: schedule) {
				this.flightSchedules.add(f);
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
