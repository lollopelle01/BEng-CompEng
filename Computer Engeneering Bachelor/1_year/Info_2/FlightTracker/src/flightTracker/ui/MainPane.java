package flightTracker.ui;

import java.io.FileReader;
import java.util.List;
import java.util.Random;

import flightTracker.model.Flight;
import flightTracker.model.Point;
import flightTracker.ui.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MainPane extends BorderPane{
	private Controller controller;
	private GeoCanvas canvas;
	private CheckBox checkBox;
	private ComboBox<String> flightCombo;
	
	public MainPane(GeoMap mappa, Controller controller) {
		this.controller=controller;
		this.canvas=new GeoCanvas(mappa);
		this.initPane();
	}
	
	private void initPane() {
		HBox panel=new HBox(2);
		{
			
			panel.getChildren().add(new Label("Voli disponibili: "));
			this.flightCombo=new ComboBox<String>(this.controller.getFlights());
			this.flightCombo.setValue(this.controller.getFlights().get(0));
			this.flightCombo.setEditable(false);
			this.flightCombo.setTooltip(new Tooltip("Scegliere il volo da graficare"));
			panel.getChildren().add(this.flightCombo);
			
			this.checkBox=new CheckBox("Voli multipli");
			this.checkBox.setIndeterminate(false);
			this.checkBox.setSelected(false);
			this.flightCombo.setOnAction(this::MyHandle);
			panel.getChildren().add(this.checkBox);
		}
		this.setTop(panel);
		
		this.setCenter(this.canvas);
		
		
	}
	
	public void MyHandle(ActionEvent e) {
		String flightName=this.flightCombo.getValue();
		try {
			Flight tmp=this.controller.load(flightName, new FileReader(flightName));
			this.plotFlight(tmp);
		}
		catch(Exception ex) {
			Controller.alert("errore", "nome del volo scorretto", flightName);
		}
	}
	
	private void plotFlight(Flight f) {
		if(f==null) {
			Controller.alert("volo passato nullo", "a fantasticaa", "ciao");
		}
		if(this.checkBox.isSelected()) {
			int r, g, b;
			Random ran=new Random();
			Color color;
			
			r=ran.nextInt(255);
			g=ran.nextInt(255);
			b=ran.nextInt(255);
			
			color=Color.rgb(g, r, b);
			this.canvas.setDrawingColor(color);
		}
		else {
			this.canvas.redrawMap();
		}
		
		List<Point> punti=this.controller.getPoints(f);
		this.canvas.drawPoints(punti);
	}
	
}
