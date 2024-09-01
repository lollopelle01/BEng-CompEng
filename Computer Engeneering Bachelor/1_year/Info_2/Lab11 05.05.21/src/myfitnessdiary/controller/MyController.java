package myfitnessdiary.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.Workout;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.ReportWriter;

public class MyController extends Controller{
	private DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).localizedBy(Locale.ITALY);

	public MyController(FitnessDiary diary, ActivityRepository activityRepository, ReportWriter reportWriter) {
		super(diary, activityRepository, reportWriter);
	}

	@Override
	public String getDayWorkout(LocalDate data) {
		String inizio="Allenamento di "+data.format(formatter);
		String lista="";
		for(Workout w: super.getFitnessDiary().getDayWorkouts(data)) {
			lista=w.getActivity().getName()+" minuti : "+w.getDuration()+" calorie bruciate : "+w.getBurnedCalories()+"\n";
		}
		String resoconto=
				"Minuti totali di allenamento: " +super.getFitnessDiary().getDayWorkoutMinutes(data)+
				"\nCalorie totali bruciate: " +super.getFitnessDiary().getDayWorkoutCalories(data);
		return inizio+"\n"+lista+"\n"+resoconto;
	}

}
