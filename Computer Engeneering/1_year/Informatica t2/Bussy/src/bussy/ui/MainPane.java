package bussy.ui;

import java.util.OptionalInt;

import bussy.model.Percorso;
import bussy.ui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainPane extends BorderPane{
	private Button bottone;
	private ComboBox<String> comboA;
	private ComboBox<String> comboB;
	private Controller controller;
	private TextArea output;
	
	public MainPane(Controller controller) {
		this.controller=controller;
		this.initPane();
	}
	
	private void initPane() {
		HBox topPane=new HBox(2);
		{
			topPane.getChildren().add(new Label("Da"));
			this.comboA=new ComboBox<String>(this.controller.getNomiFermate());
			topPane.getChildren().add(this.comboA);
			
			topPane.getChildren().add(new Label("A"));
			this.comboB=new ComboBox<String>(this.controller.getNomiFermate());
			topPane.getChildren().add(this.comboB);
		}
		topPane.setAlignment(Pos.CENTER);
		this.setTop(topPane);
		
		this.output=new TextArea();
		this.output.setEditable(false);
		this.setCenter(this.output);
		
		HBox bottomPane=new HBox(2);
		{
			this.bottone=new Button("Cerca percorso");
			this.bottone.setOnAction(this::cerca);
			this.bottone.setCenterShape(true);
			bottomPane.getChildren().add(this.bottone);
		}
		bottomPane.setAlignment(Pos.CENTER);
		this.setBottom(bottomPane);
	}
	
	private void cerca(ActionEvent e) {
		var percorsi=this.controller.cercaPercorsi(this.comboA.getValue(), this.comboB.getValue(), OptionalInt.empty());
		if(percorsi.isEmpty()) {
			this.output.setText("Nessun percorso trovato");
		}
		else {
			StringBuilder sb=new StringBuilder();
			for(Percorso p: percorsi) {
				sb.append(p.toString()+"\n");
			}
			this.output.setText(sb.toString());
		}
			
	}
}
