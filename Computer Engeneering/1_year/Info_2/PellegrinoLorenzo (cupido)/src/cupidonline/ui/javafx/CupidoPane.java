//package cupidonline.ui.javafx;
//
//import cupidonline.model.Corrispondenza;
//import cupidonline.model.Persona;
//import cupidonline.ui.controller.Controller;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.VBox;
//
//public class CupidoPane extends BorderPane{
//	private Controller controller;
//	private ComboBox<String> persone;
//	private Button cerca;
//	private TextArea output;
//	private PreferenzePane preferenze;
//	
//	private void cerca(ActionEvent e) {
//		Corrispondenza c=controller.get
//		this.output.setText();
//	}
//	
//	VBox panel=new VBox(3);
//	{
//		this.output=new TextArea();
//		
//		panel.getChildren().add(new Label("Ricerca corrispondenze con:"));
//		this.persone=new ComboBox<String>(FXCollections.observableArrayList(controller.getNomiIscritti()));
//		this.persone.setEditable(false);
//		this.persone.setValue("NON ISCRITTO");
//		panel.getChildren().add(this.persone);
//		
//		this.cerca=new Button("Cerca");
//		this.cerca.setAlignment(Pos.BASELINE_LEFT);
//		this.cerca.setOnAction(this::cerca);
//	}
//	
//}


package cupidonline.ui.javafx;

import cupidonline.model.Preferenza;
import cupidonline.ui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CupidoPane extends BorderPane {

	private TextArea outputArea;
	private Controller controller;
	private Button buttonCerca;
	private VBox leftPane;
	private ComboBox<String> comboIscritti;
	private PreferenzePane prefPane; 
	
	public CupidoPane(Controller controller) {
		this.controller = controller;
		//
		leftPane = new VBox();
		leftPane.getChildren().add(new Label(" Ricerca corrispondenze con: "));
		HBox hbox = new HBox();
			comboIscritti = new ComboBox<>(this.controller.getNomiIscritti());
			comboIscritti.getItems().add(0,"NON ISCRITTO");
			buttonCerca = new Button("Cerca");
			buttonCerca.setOnAction(this::cercaCorrispondenze);
			Region r1 = new Region(); HBox.setHgrow(r1, Priority.ALWAYS);
			Region r2 = new Region(); HBox.setHgrow(r2, Priority.ALWAYS);
			hbox.getChildren().addAll(comboIscritti, r1, buttonCerca, r2);
		leftPane.getChildren().add(hbox);
		comboIscritti.setValue(comboIscritti.getItems().get(0));
		//
		prefPane = new PreferenzePane();
		leftPane.getChildren().add(prefPane);
		this.setLeft(leftPane);
		comboIscritti.setOnAction(this::aggiornaPrefPane);

		outputArea = new TextArea();
		outputArea.setEditable(false);
		outputArea.setPrefWidth(600);
		outputArea.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		outputArea.setText("");
		this.setRight(outputArea);

	}

	private void aggiornaPrefPane(ActionEvent ev) {
		prefPane.setPreferenza(controller.getPreferenza(comboIscritti.getValue()));
	}

	private void cercaCorrispondenze(ActionEvent event) {
		String pName = this.comboIscritti.getValue();
		this.outputArea.setText("");
		if (!pName.equalsIgnoreCase("NON ISCRITTO")) {
			controller.trovaCorrispondenze(pName).forEach(corr -> this.outputArea.appendText(corr.toString()+"\n"));
		}
		else { // non iscritto
			Preferenza pref = prefPane.getPreferenza();
			controller.trovaCorrispondenze(pName, pref).forEach(corr -> this.outputArea.appendText(corr.toString()+"\n"));
		}
		this.outputArea.appendText("Non ci sono altre corrispondenze\n");
		
	}


}

