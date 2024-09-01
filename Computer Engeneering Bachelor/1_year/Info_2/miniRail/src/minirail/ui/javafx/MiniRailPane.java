package minirail.ui.javafx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import minirail.model.Train;
import minirail.ui.controller.Controller;

public class MiniRailPane extends BorderPane{
	private Button clock;
	private Controller controller;
	private Label label;
	private Button moving;
	private TextArea output;
	private Button stopping;
	private Map<Train, Color> trainColors=new HashMap<>();
	
	public MiniRailPane(Controller controller, double[] posizioni) {
		this.controller=controller;
		
		HBox topPane=new HBox(2);
		{
			this.moving=new Button("Move trains");
			this.moving.setOnAction(this::move);
			topPane.getChildren().add(this.moving);
			
			this.stopping=new Button("Stop trains");
			this.stopping.setOnAction(this::stop);
			topPane.getChildren().add(this.stopping);
			
			this.label=new Label("Trains are now STOPPED");
			topPane.getChildren().add(this.label);
		}
		this.setTop(topPane);
		
		this.setupTrainPane(posizioni);
		
		this.clock=new Button("Clock");
		this.clock.setOnAction(this::clock);
		this.setRight(this.clock);
		
		this.output=new TextArea();
		this.output.setEditable(false);
		this.setBottom(this.output);
		
		}
	
	private void clock(ActionEvent e) {
		this.controller.clock(0.5);
		this.output.appendText(this.controller.getLog());
		this.updateTrainPanel();
	}
	
	private void updateTrainPanel() {
		TrainLinePane linePane=new TrainLinePane(this.controller.getLine());
		for(Train t: this.trainColors.keySet()) {
			if(this.controller.getLineStatus().putTrain(t, this.controller.getLineStatus().getTrainLocation(t))) {
				linePane.drawTrain(this.controller.getLineStatus().getTrainLocation(t), t.getLength(), this.trainColors.get(t), t.getName());
			}
		}
		this.setCenter(linePane);
	}
	
	private void setupTrainPane(double[] posizioni) {
		TrainLinePane linePane=new TrainLinePane(this.controller.getLine());
		this.drawTrainColors();
		List<Train> treni=this.controller.getTrains();
		
		for(int i=0; i<treni.size(); i++) {
			Train t=treni.get(i);
			if(this.controller.getLineStatus().putTrain(t, posizioni[i])){
				linePane.drawTrain(posizioni[i], t.getLength(), this.trainColors.get(t), t.getName());
			}
		}
		this.setCenter(linePane);
	}
	
	private void drawTrainColors() {
		for(Train t: this.controller.getTrains()) {
			this.trainColors.put(t, this.randomColor());
		}
	}
	
	private void move(ActionEvent e) {
		for(Train t: this.controller.getTrains()) {
			this.controller.setMoving(t);
		}
		this.label.setText("Trains are now MOVING");
	}
	
	private void stop(ActionEvent e) {
		for(Train t: this.controller.getTrains()) {
			this.controller.setStopped(t);
		}
		this.label.setText("Trains are now STOPPED");
	}
	
	private Color randomColor() {
		Random r=new Random();
		return Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
	
}
