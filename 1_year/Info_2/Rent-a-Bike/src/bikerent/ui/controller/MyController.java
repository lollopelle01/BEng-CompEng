package bikerent.ui.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import bikerent.model.Calculator;
import bikerent.model.MyCalculator;
import bikerent.model.MyRent;
import bikerent.model.Rate;
import bikerent.model.Rent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyController implements Controller{
	private Calculator calculator;
	private Map<String, Rate> mappaTariffe;
	
	public MyController(Map<String, Rate> mappaTariffe) {
		super();
		this.mappaTariffe = mappaTariffe;
		this.calculator=new MyCalculator();
	}

	@Override
	public ObservableList<String> getCityNames() {
		return FXCollections.observableArrayList(this.mappaTariffe.keySet());
	}

	@Override
	public double getRentCost(String citta, LocalDate dataInizio, LocalTime oraInizio, LocalDate dataFine, LocalTime oraFine) {
		Rate rate=this.mappaTariffe.get(citta);
		LocalDateTime inizio=LocalDateTime.of(dataInizio, oraInizio);
		LocalDateTime fine=LocalDateTime.of(dataFine, oraFine);
		Rent rent=new MyRent(inizio, fine, rate);
		
		return this.calculator.calc(rate,rent );
	}

}
