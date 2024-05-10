package rfd.model;

import java.time.Duration;
import java.util.List;

public interface TripDurationCalculator {
	public Duration getDuration(Route route);
}
