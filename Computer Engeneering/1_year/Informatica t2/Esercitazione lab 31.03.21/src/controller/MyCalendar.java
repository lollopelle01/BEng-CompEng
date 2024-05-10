package controller;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

import model.Appointment;
import model.AppointmentCollection;
import modelTest.*;

public class MyCalendar {
	private AppointmentCollection appuntamenti;
	
	private boolean isOverlapped(LocalDateTime start, LocalDateTime end, LocalDateTime refStart, LocalDateTime refEnd) {
		if(end.isBefore(refEnd)) {
            if(end.isAfter(refStart)) {
                return true;
            } else {
                return false;
            }
        } else {
            if(start.isBefore(refEnd)) {
                return true;
            } else {
                return false;
            }
        }
	}
	private AppointmentCollection getAppointmentsIn(LocalDateTime start, LocalDateTime end) {
		var result=new AppointmentCollection();
		
		for(int i=0; i<appuntamenti.size(); i++) {
			if(isOverlapped(appuntamenti.get(i).getFrom(), appuntamenti.get(i).getTo(), start, end)) {
				result.add(appuntamenti.get(i));
			}
		}
		return result;
	}
	
	public MyCalendar() {
		this.appuntamenti=new AppointmentCollection();
	}
 	public void add(Appointment a1) {
		int trovato=0;
		for(int i=0; i<appuntamenti.size() && trovato==0; i++) {
			if(isOverlapped(appuntamenti.get(i).getFrom(), appuntamenti.get(i).getTo(), a1.getFrom(), a1.getTo())) {
				trovato=1;
			}
		}
		if(trovato==0) {
			appuntamenti.add(a1);
		}
	}
	public boolean remove(Appointment a) {
		int trovato=0, i=0;
		for(i=0; i<appuntamenti.size() && trovato==0; i++) {
			if(appuntamenti.get(i).equals(a)) {
				trovato=1;
				appuntamenti.remove(i);
			}
		}
		if(trovato==0)
			return false;
		else
			return true;
	}
	public AppointmentCollection getAllAppointments() {
		return this.appuntamenti;
	}
	public AppointmentCollection getDayAppointments(LocalDate date) {
		 LocalDateTime startDate = date.atStartOfDay();
		 LocalDateTime endDate = startDate.plusDays(1);
		 
		 return this.getAppointmentsIn(startDate,endDate);
	}
	public AppointmentCollection getMonthAppointments(LocalDate date) {
		LocalDateTime startDate = LocalDateTime.of(date.getYear(),date.getMonth(),1,1,1);
        LocalDateTime endDate = startDate.plusMonths(1);

        return this.getAppointmentsIn(startDate,endDate);
	}
	public AppointmentCollection getWeekAppointments(LocalDate date) {
		TemporalField fieldISO = WeekFields.of(Locale.ITALY).dayOfWeek();
        LocalDateTime startDate = LocalDateTime.of(date.with(fieldISO, 1), LocalTime.of(0,0));
        LocalDateTime endDate = startDate.plusWeeks(1);

        return this.getAppointmentsIn(startDate,endDate);
	}
	
}
