package flights.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import flights.model.Airport;
import flights.model.FlightSchedule;
import flights.persistence.DataManager;

public class MyController implements Controller{
	private DataManager dataManager;
	private List<Airport> sortedAirports;
	
	public MyController(DataManager dataManager) {
		super();
		this.dataManager=dataManager;
		this.sortedAirports=new ArrayList<Airport>(this.dataManager.getAirportMap().values());
		
		Comparator<Airport> comp1=Comparator.comparing(a -> a.getCity().getName());
		Comparator<Airport> comp2=comp1.thenComparing(a -> a.getName());
		this.sortedAirports.sort(comp2);
	}
	
	@Override
	public List<Airport> getSortedAirports() {
		return this.sortedAirports;
	}

	@Override
	public List<FlightSchedule> searchFlights(Airport departureAirport, Airport arrivalAirport, LocalDate date) {
		Collection<FlightSchedule> elenco=dataManager.getFlightSchedules();
		List<FlightSchedule> result=new ArrayList<FlightSchedule>();
		var giorno=date.getDayOfWeek();
		for(FlightSchedule f: elenco) {
			boolean c1=f.getArrivalAirport().equals(arrivalAirport);
			boolean c2=f.getDepartureAirport().equals(departureAirport);
			boolean c3=false;
			if(c1 && c2) {
				for(DayOfWeek d: f.getDaysOfWeek()) {
					if(d.equals(giorno)) {
						c3=true;
					}
				}
			}
			if(c1 && c2 && c3) {
				result.add(f);
			}
		}
		return result;
	}

}
