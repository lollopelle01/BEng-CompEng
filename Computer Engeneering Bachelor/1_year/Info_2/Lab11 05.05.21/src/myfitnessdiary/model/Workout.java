package myfitnessdiary.model;

import java.time.LocalDate;

public class Workout {
	private Activity activity;
	private LocalDate date;
	private int duration;
	private Intensity intensity;
	
	public Activity getActivity() {
		return activity;
	}
	public LocalDate getDate() {
		return date;
	}
	public int getDuration() {
		return duration;
	}
	public Intensity getIntensity() {
		return intensity;
	}
	
	public Workout(LocalDate date, int duration, Intensity intensity, Activity activity) throws IllegalArgumentException{
		if(date!=null && duration>0 && intensity!=null && activity!=null) {
			this.activity = activity;
			this.date = date;
			this.duration = duration;
			this.intensity = intensity;
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	public int getBurnedCalories() throws IllegalArgumentException{
		return activity.getCalories(intensity)*duration;
	}
	
	
}
