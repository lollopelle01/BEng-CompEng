package electriclife.ui.controller;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import electriclife.model.Bolletta;
import electriclife.model.Tariffa;
import electriclife.persistence.MyBollettaWriter;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ElectricLifePane extends BorderPane{
	private Bolletta bolletta;
	private Button calcolaButton;
	private Button stampaButton;
	private TextField campoConsumo;
	private ComboBox<Tariffa> comboTariffe;
	private Controller controller;
	private VBox leftPane;
	private TextArea output;
	private DatePicker picker; 
	
	public ElectricLifePane(Controller controller) {
		this.controller=controller;
		this.initPane();
	}
	
	private void initPane() {
		
		this.leftPane=new VBox(2);
		{
			this.leftPane.getChildren().add(new Label("Tariffe disponibili"));
			this.comboTariffe=new ComboBox<Tariffa>(this.controller.getTariffe());
			this.comboTariffe.setValue(this.controller.getTariffe().get(0));
			this.leftPane.getChildren().add(this.comboTariffe);
			
			this.leftPane.getChildren().add(new Label("consumo in KWh"));
			this.campoConsumo=new TextField();
			this.campoConsumo.setEditable(true);
			this.leftPane.getChildren().add(this.campoConsumo);
			
			this.leftPane.getChildren().add(new Label("Data di emissione"));
			this.picker=new DatePicker();
			this.picker.setValue(LocalDate.now());
			this.leftPane.getChildren().add(this.picker);
			
		}
		this.setLeft(this.leftPane);
		
		HBox bottomPane=new HBox(2);
		{
			this.calcolaButton=new Button("Calcola");
			this.calcolaButton.setOnAction(this::calcola);
			bottomPane.getChildren().add(this.calcolaButton);
			
			this.stampaButton=new Button("Stampa");
			this.stampaButton.setOnAction(this::stampa);
			this.stampaButton.setVisible(false);
			bottomPane.getChildren().add(this.stampaButton);
		}
		this.setBottom(bottomPane);
		
		this.output=new TextArea();
		this.output.setEditable(false);
		this.setRight(this.output);
		
	}
	
	private void calcola(ActionEvent e) {
		try{
			this.bolletta=this.controller.creaBolletta(
					this.picker.getValue(), 
					this.comboTariffe.getValue().getNome(),
					NumberFormat.getInstance(Locale.ITALY).parse(this.campoConsumo.getText()).intValue()
					);
			this.output.setText(this.bolletta.toString());
		}
		catch(Exception ex) {
			Controller.alert("numero inserito nel formato errato", "", "");
		}
		this.stampaButton.setVisible(true);
	}
	
	private void stampa(ActionEvent e) {
		try {
			this.controller.stampaBolletta(this.bolletta, new MyBollettaWriter(new PrintWriter("Bolletta.txt")));
		}
		catch(Exception ex) {
			Controller.alert("Errore lettura file", "", "");
		}
		this.stampaButton.setVisible(false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
