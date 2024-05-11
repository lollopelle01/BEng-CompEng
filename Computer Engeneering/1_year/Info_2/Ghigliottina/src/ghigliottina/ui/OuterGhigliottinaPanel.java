package ghigliottina.ui;


import ghigliottina.model.Ghigliottina;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class OuterGhigliottinaPanel extends BorderPane {
 
	private GhigliottinaPanel gPanel;
	private TextField txtRispostaUtente, txtRispostaEsatta;
	private Label rightLabel, leftLabel;
	private Button svela;
	private String rispostaEsatta;
	private Controller controller;
	private Ghigliottina gh;
	private int montepremi;
	
	public OuterGhigliottinaPanel(int montepremi, Controller controller) {
		this.controller=controller;
		this.montepremi=montepremi;
		setupGhigliottinaPanel();
		//
		HBox revealBox = new HBox();
		{
			revealBox.setAlignment(Pos.CENTER);
			revealBox.setStyle("-fx-background-color: red;");
			
			VBox revealBox1= new VBox();
			{
				this.leftLabel=new Label("La parola segreta: ");
				this.leftLabel.setPrefWidth(150);
				this.txtRispostaEsatta=new TextField();
				this.txtRispostaEsatta.setEditable(false);
				revealBox1.getChildren().addAll(this.leftLabel, this.txtRispostaEsatta);
			}
			revealBox.getChildren().add(revealBox1);
			
			
			VBox revealBox2= new VBox();
			{
				this.rightLabel=new Label("La tua risposta: ");
				this.rightLabel.setPrefWidth(150);
				this.txtRispostaUtente=new TextField();
				this.txtRispostaUtente.setEditable(true);
				revealBox2.getChildren().addAll(this.rightLabel, this.txtRispostaUtente);
			}
			revealBox.getChildren().add(revealBox2);
			
			this.svela=new Button("Svela");
			this.svela.setPrefSize(100, 50);;
			this.svela.setOnAction(this::svela);
			revealBox.getChildren().add(this.svela);
		}
			
		this.setTop(revealBox);
	}
	
	private void setupGhigliottinaPanel() {
		// initial setup
		gh = controller.sorteggiaGhigliottina();
		this.rispostaEsatta=gh.getRispostaEsatta();
		gPanel = new GhigliottinaPanel(montepremi, gh.getTerne());
		this.setBottom(gPanel);
	}
	
	private void reset() {
		setupGhigliottinaPanel();
		txtRispostaUtente.setText("");
		txtRispostaEsatta.setText("");
	}
	

	private void svela(ActionEvent e) {
		this.txtRispostaEsatta.setText(gh.getRispostaEsatta());
		if(!this.txtRispostaUtente.getText().isEmpty()) {
			if(this.txtRispostaUtente.getText().equalsIgnoreCase(this.txtRispostaEsatta.getText())) {
				GhigliottinaPanel.alert("Risultato", "Hai vinto!", "montepremi: "+this.montepremi+" €");
			}
			else {
				GhigliottinaPanel.alert("Risultato", "RISPOSTA SBAGLIATA :(", "purtroppo hai perso: "+this.montepremi+" €");
			}
		}
		else {
			GhigliottinaPanel.alert("Errore", "Risposta mancante", "non hai scritto alcuna parola valida");
		}
		
		this.reset();
		
	}


	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	public static void info(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

}
