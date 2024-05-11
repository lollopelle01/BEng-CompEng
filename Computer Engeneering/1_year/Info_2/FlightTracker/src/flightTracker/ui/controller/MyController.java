package flightTracker.ui.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import flightTracker.model.Flight;
import flightTracker.model.FlightPos;
import flightTracker.model.Point;
import flightTracker.persistence.BadFileFormatException;
import flightTracker.persistence.FlightReader;

public class MyController extends Controller{

	public MyController(String[] availableFlightFiles) {
		super(availableFlightFiles);
	}

	@Override
	public List<Point> getPoints(Flight flight) {
		List<Point> result=new ArrayList<Point>();
		for(FlightPos pos: flight.getPositions()) {
			result.add(pos.getPosition());
		}
		return result;
	}

	@Override
	public Flight load(String flightId, Reader reader) throws IOException, BadFileFormatException {
		BufferedReader br=new BufferedReader(reader);
		FlightReader fr= FlightReader.of();
		return fr.readFlight(flightId, br);
	}

}
