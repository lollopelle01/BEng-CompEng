package rfd.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MyTripDurationCalculator implements TripDurationCalculator{

	@Override
	public Duration getDuration(Route route) {
		double time=0;
		for(Segment s: route.getRouteSegments()) {
			s=s.normalize();
			for(Segment s1: s.split()) {
				time=time+s1.getLength()/s1.getSpeed();
			}
		}
		return Duration.ofMinutes(Math.round(time*60));
	}

}
