package flights.ui;



import java.time.LocalDate;
import java.util.List;
import flights.controller.Controller;
import flights.model.Airport;
import flights.model.FlightSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane{
	private Controller controller;
	private ComboBox<Airport> departureAirportComboBox;
	private ComboBox<Airport> arrivalAirportComboBox;
	private DatePicker departureDatePicker;
	private FlightScheduleListPanel flightScheduleListPanel;
	
	private void MyHandle(ActionEvent event) {
		Airport departureAirport=this.departureAirportComboBox.getValue();
		Airport arrivalAirport=this.arrivalAirportComboBox.getValue();
		LocalDate date=this.departureDatePicker.getValue();
		
		List<FlightSchedule> flightSchedules=controller.searchFlights(departureAirport, arrivalAirport, date);
		flightScheduleListPanel.setFlightSchedules(flightSchedules);
	}
	
	private void initPane() {
		VBox leftPane=new VBox();
		leftPane.setSpacing(10);
		leftPane.setPadding(new Insets(0, 20, 10, 20));
		
		Label label=new Label("Departure Airport");
		leftPane.getChildren().add(label);
		
		List<Airport> airports=controller.getSortedAirports();
		ObservableList<Airport> observableAirports=FXCollections.observableArrayList(airports);
		
		this.departureAirportComboBox=new ComboBox<Airport>(observableAirports);
		this.departureAirportComboBox.setEditable(false);
		this.departureAirportComboBox.setValue(observableAirports.get(0));
		leftPane.getChildren().add(this.departureAirportComboBox);
		
		Label label2=new Label("Arrival Airport");
		leftPane.getChildren().add(label2);
		
		this.arrivalAirportComboBox=new ComboBox<Airport>(observableAirports);
		this.arrivalAirportComboBox.setEditable(false);
		this.arrivalAirportComboBox.setValue(observableAirports.get(0));
		leftPane.getChildren().add(this.arrivalAirportComboBox);
		
		Label label3=new Label("Departure Date");
		leftPane.getChildren().add(label3);
		
		this.departureDatePicker=new DatePicker(LocalDate.now());
		this.departureDatePicker.setValue(LocalDate.now());
		leftPane.getChildren().add(this.departureDatePicker);
		
		Button button=new Button("Find");
		button.setOnAction(this::MyHandle);
		
		
		leftPane.setAlignment(Pos.BASELINE_RIGHT);
		setLeft(leftPane);

//		BorderPane.setMargin(leftPane, new Insets(15, 0, 0, 10));

		flightScheduleListPanel = new FlightScheduleListPanel();
		setCenter(flightScheduleListPanel);
	}
	
	public MainPane(Controller c) {
		this.controller=c;
		this.initPane();
	}

}
