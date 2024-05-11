package bikerent.ui.javafx;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import bikerent.ui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RentABikePane extends BorderPane{
	private Button bottoneTicket;
	private VBox centerPane;
	private ComboBox<String> combo;
	private Controller controller;
	private TextField fieldFine;
	private TextField fieldInizio;
	private DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
	private VBox leftPane;
	private TextArea output;
	private DatePicker pickerFine;
	private DatePicker pickerInizio;
	
	public RentABikePane(Controller controller) {
		this.controller=controller;
		this.initiPane();
	}
	
	private void initiPane() {
		this.output=new TextArea();
		this.output.setEditable(false);
		
		
		this.leftPane=new VBox(2);
		{
			this.leftPane.getChildren().add(new Label("Città: "));
			this.combo=new ComboBox<String>(this.controller.getCityNames());
			this.combo.setEditable(false);
			this.combo.setValue(this.controller.getCityNames().get(0));
			this.leftPane.getChildren().add(this.combo);
			
			this.leftPane.getChildren().add(new Label("Inizio noleggio: "));
			this.pickerInizio=new DatePicker(LocalDate.now());
			this.pickerInizio.setEditable(false);
			this.leftPane.getChildren().add(this.pickerInizio);
			
			this.fieldInizio=new TextField();
			this.fieldInizio.setEditable(true);
			this.leftPane.getChildren().add(this.fieldInizio);
			
		}
		this.setLeft(this.leftPane);
		
		this.centerPane=new VBox(2);
		{
			this.bottoneTicket=new Button("Calcola costo");
			this.bottoneTicket.setStyle("-fx-background-color: Red");
			this.bottoneTicket.setOnAction(this::calcolaCostoNoleggio);
			this.centerPane.getChildren().add(this.bottoneTicket);
			
			this.centerPane.getChildren().add(new Label("Fine noleggio: "));
			this.pickerFine=new DatePicker(LocalDate.now());
			this.pickerFine.setEditable(false);
			this.centerPane.getChildren().add(this.pickerFine);
			
			this.fieldFine=new TextField();
			this.fieldFine.setEditable(true);
			this.centerPane.getChildren().add(this.fieldFine);
		}
		this.setCenter(this.centerPane);
		
		this.setRight(this.output);
	}
	
	private void calcolaCostoNoleggio(ActionEvent e) {
		if(this.combo.getValue()==null) {
			Controller.alert("errore citta", "città non inserita", "la stringa della città è null");
		}
		if(this.pickerInizio.getValue().isAfter(this.pickerFine.getValue())) {
			Controller.alert("errore orario", "impossibile tornare indietro nel tempo con le date", "per ora");
		}
		try {
			LocalTime inizio=LocalTime.parse(this.fieldInizio.getText());
			LocalTime fine=LocalTime.parse(this.fieldFine.getText());
			if(inizio.isAfter(fine)) {
				Controller.alert("errore orario", "impossibile tornare indietro nel tempo con i giorni", "per ora");
			}
			double prezzo=this.controller.getRentCost(this.combo.getValue(), this.pickerInizio.getValue(), inizio, this.pickerFine.getValue(), fine);
			this.output.setText(NumberFormat.getCurrencyInstance(Locale.ITALY).format(prezzo));
		}
		catch(Exception ex) {
			Controller.alert( "errore lettura data", "formato hh:mm non rispettato", "svegliaa");
		}
		
		
		
	}
}
