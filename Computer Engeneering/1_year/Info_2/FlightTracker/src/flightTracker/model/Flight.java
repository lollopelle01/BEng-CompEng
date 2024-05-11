package flightTracker.model;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class Flight{
	private String id;
	private List<FlightPos> tracks;
	
	public Flight(String id, List<FlightPos> tracks) {
		if(id==null || id.isBlank() || tracks==null || tracks.isEmpty()) throw new IllegalArgumentException();
		this.id = id;
		this.tracks = tracks;
	}
	
	public Duration getDuration() {
		ZonedDateTime inizio=tracks.get(0).getTimestamp();
		ZonedDateTime fine=tracks.get(tracks.size()-1).getTimestamp();
		return  Duration.between(inizio, fine);
	}

	public String getId() {
		return id;
	}

	public List<FlightPos> getPositions() {
		return tracks;
	}
}
