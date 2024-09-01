package flights.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import flights.model.Aircraft;
import flights.model.Airport;
import flights.model.FlightSchedule;

public class MyFlightScheduleReader implements FlightScheduleReader{
	private DateTimeFormatter timeFormatter=DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).localizedBy(Locale.ITALY);
	
	private ArrayList<DayOfWeek> readDaysOfWeek(String days) throws BadFileFormatException{
		var result=new ArrayList<DayOfWeek>();
		for(int i=0; i<7; i++) {
			if( "1234567".charAt(i)==days.charAt(i)) {
				result.add(DayOfWeek.of(i+1));
			}
			else if(days.charAt(i)!='-') {
				throw new BadFileFormatException();
			}
		}
		return result;
	}
	
	private FlightSchedule readSchedule(BufferedReader reader, Map<String, Airport> airportMap, Map<String, Aircraft> aircraftMap) 
			throws IOException, BadFileFormatException {
		String riga=reader.readLine();
		if(riga==null || riga.trim().isEmpty()) {
			return null;
		}
		StringTokenizer tokenizer=new StringTokenizer(riga, ",");
		try {
			String code=tokenizer.nextToken();
			
			String token=tokenizer.nextToken();
			Airport departureAirport=airportMap.get(token);
			if(departureAirport==null) {
				throw new BadFileFormatException();
			}
			
			token=tokenizer.nextToken();
			LocalTime departureLocalTime=LocalTime.parse(token, timeFormatter);
			
			token=tokenizer.nextToken();
			Airport arrivalAirport=airportMap.get(token);
			if(arrivalAirport==null) {
				throw new BadFileFormatException();
			}
			
			token=tokenizer.nextToken();
			LocalTime arrivalLocalTime=LocalTime.parse(token, timeFormatter);
			
			token=tokenizer.nextToken();
			int dayOffset=Integer.parseInt(token);
			
			token=tokenizer.nextToken();
			ArrayList<DayOfWeek> daysOfWeek= this.readDaysOfWeek(token);
			
			token=tokenizer.nextToken();
			Aircraft aircraft=aircraftMap.get(token);
			if(aircraft==null) {
				throw new BadFileFormatException();
			}
			
			FlightSchedule schedule=new FlightSchedule(code, departureAirport, departureLocalTime, 
					arrivalAirport, arrivalLocalTime, dayOffset, daysOfWeek, aircraft);
			return schedule;
		}
		catch(BadFileFormatException e){
			throw e;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
	
	@Override
	public Collection<FlightSchedule> read(Reader reader, Map<String, Airport> airportMap,
			Map<String, Aircraft> aircraftMap) throws IOException, BadFileFormatException {
		BufferedReader bufferedReader=new BufferedReader(reader);
		ArrayList<FlightSchedule> schedules=new ArrayList<FlightSchedule>();
		FlightSchedule schedule;
		while((schedule= this.readSchedule(bufferedReader, airportMap, aircraftMap)) !=null) {
			schedules.add(schedule);
		}
		return schedules;
	}

}
