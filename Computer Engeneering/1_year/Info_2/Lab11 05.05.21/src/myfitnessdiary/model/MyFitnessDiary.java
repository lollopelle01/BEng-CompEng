package myfitnessdiary.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyFitnessDiary implements FitnessDiary{
	private List<Workout> workouts;

	
	public MyFitnessDiary() {
		this.workouts=new ArrayList<Workout>();
	}

	@Override
	public List<Workout> getWeekWorkouts(LocalDate date) {
		List<Workout> result= new ArrayList<Workout>();
		long distanzaGiorni=date.getDayOfWeek().getValue()-DayOfWeek.MONDAY.getValue();
		LocalDate inizioP=date.minusDays(distanzaGiorni);
		LocalDate fineP=inizioP.plusWeeks(1).minusDays(1);
		for(Workout w : workouts) {
			if(w.getDate().compareTo(inizioP)>=0 && w.getDate().compareTo(fineP)<=0) {
				result.add(w);
			}
		}
		return result;
	}

	@Override
	public List<Workout> getDayWorkouts(LocalDate date) {
		List<Workout> result= new ArrayList<Workout>();
		for(Workout w : workouts) {
			if(w.getDate().equals(date)) {
				result.add(w);
			}
		}
		return result;
	}

	@Override
	public void addWorkout(Workout wo) {
		workouts.add(wo);
		
	}

	@Override
	public int getWeekWorkoutCalories(LocalDate date) {
		List<Workout> list= new ArrayList<Workout>();
		list=this.getWeekWorkouts(date);
		int result=0;
		for(Workout w: list) {
			result=result+w.getBurnedCalories();
		}
		return result;
	}

	@Override
	public int getDayWorkoutCalories(LocalDate date) {
		List<Workout> list= new ArrayList<Workout>();
		list=this.getDayWorkouts(date);
		int result=0;
		for(Workout w: list) {
			result=result+w.getBurnedCalories();
		}
		return result;
	}

	@Override
	public int getWeekWorkoutMinutes(LocalDate date) {
		List<Workout> list= new ArrayList<Workout>();
		list=this.getWeekWorkouts(date);
		int result=0;
		for(Workout w: list) {
			result=result+w.getDuration();
		}
		return result;
	}

	@Override
	public int getDayWorkoutMinutes(LocalDate date) {
		List<Workout> list= new ArrayList<Workout>();
		list=this.getDayWorkouts(date);
		int result=0;
		for(Workout w: list) {
			result=result+w.getDuration();
		}
		return result;
	}

}
