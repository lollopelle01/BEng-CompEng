package dentinia.governor.ui.javafx;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;
import dentinia.governor.ui.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ElectionPane extends BorderPane{
	private Slider sbarramento;
	private TextArea electionTable, percentageTable;
	private Controller controller;
	private Button calcola, salva;
	private FlowPane rightPane;
	private PieChart chart; 
	
	public ElectionPane(Controller controller) {
		this.controller=controller;
		this.initPane();
	}
	
	private String calcolaPercentuali(Elezioni e ) {
		StringBuilder sb=new StringBuilder();
		for(Partito p: e.getPartiti()) {
			sb.append(p.getNome()
					+"\t\t\t\t Voti: "+(e.getVoti(p)/e.getVotiTotali())*100
					+"% \t\t\t\t Seggi: "+(e.getRisultato().getSeggi(p)/e.getSeggiDaAssegnare())*100
					+"%\n");
		}
		return sb.toString();
	}
	
	private void disegnaGrafico(Elezioni e) {
		ObservableList<PieChart.Data> list =FXCollections.observableArrayList();
		for(Partito p: e.getPartiti()) {
			list.add(new PieChart.Data(p.getNome(), e.getRisultato().getSeggi(p)));
		}
		this.chart=new PieChart(list);
		this.chart.setLabelsVisible(false); //toglie le freccette
		
		this.rightPane=new FlowPane();
		this.rightPane.getChildren().add(new Label("Distribuzuone seggi"));
		this.rightPane.getChildren().add(this.chart);
	}
	
	private void initPane() {
		
		HBox topPanel=new HBox(2);
		{
			topPanel.getChildren().add(new Label("Sbarramento %"));
			this.sbarramento=new Slider(0,10, 0); //min, max, corrente
			this.sbarramento.setBlockIncrement(0.5); //incremento con frecce
			this.sbarramento.setSnapToTicks(true); //scatta al blockIncrement successivo
			this.sbarramento.setMajorTickUnit(1); //disegna multipli di uno
			this.sbarramento.setShowTickLabels(true); //mostra i disegni
			this.sbarramento.setShowTickMarks(true); //mostra gradazione
			this.sbarramento.setPrefWidth(300); //setta lunghezza
			topPanel.getChildren().add(this.sbarramento);
		}
		this.setTop(topPanel);
		
		VBox leftPane=new VBox(2);
		{
			this.electionTable=new TextArea();
			this.electionTable.setEditable(false);
			leftPane.getChildren().add(this.electionTable);
			
			this.percentageTable=new TextArea();
			this.percentageTable.setEditable(false);
			leftPane.getChildren().add(this.percentageTable);
		}
		this.setLeft(leftPane);
		
		HBox bottomPanel=new HBox(2);
		{
			this.calcola=new Button("Calcola");
			this.calcola.setOnAction(this::calcola);
			bottomPanel.getChildren().add(this.calcola);
			
			this.salva=new Button("Salva");
			this.salva.setOnAction(this::salva);
			bottomPanel.getChildren().add(this.salva);
		}
		this.setBottom(bottomPanel);
		
		this.electionTable.setText(controller.getElezioni().toString());
		percentageTable.setText(calcolaPercentuali(controller.getElezioni()));
		disegnaGrafico(controller.getElezioni());

	}
	
	private void calcola(ActionEvent e) {
		Elezioni elezioni=this.controller.ricalcola(this.sbarramento.getValue()/100);
		this.electionTable.setText(elezioni.toString());
		this.percentageTable.setText(this.calcolaPercentuali(elezioni));
		this.salva.setDisable(false);
		this.disegnaGrafico(elezioni);
	}
	
	private void salva(ActionEvent e) {
		try {
			this.controller.salvaSuFile("Metodo D'Hondt con sbarramento del " + this.sbarramento.getValue() + "%");
			this.salva.setDisable(true);
		}
		catch(Exception ex) {
			this.controller.alert("errore scrittura su file", "", "");
		}
	}
	
	
	
}
