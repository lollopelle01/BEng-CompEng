package cambiavalute.ui.controller;

import java.text.ParseException;
import java.util.OptionalDouble;

import cambiavalute.model.CambiaValute;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainFrame extends BorderPane{
	private Controller controller;
	private ComboBox<String> valuta;
	private RadioButton acquisto;
	private RadioButton vendita;
	private TextField importo;
	private TextField cambio;
	private TextArea output;
	
	public MainFrame(Controller controller) {
		this.controller=controller;
		this.initFrame();
	}
	
	private void initFrame() {
		VBox panel=new VBox();
		{
			panel.setAlignment(Pos.CENTER);
			
			this.output=new TextArea();
			
			panel.getChildren().add(new Label("Valuta"));
			this.valuta=new ComboBox<String>(FXCollections.observableArrayList(controller.getListaValuteEstere()));
			this.valuta.setEditable(false);
			panel.getChildren().add(this.valuta);
			
			panel.getChildren().add(new Label("Importo"));
			this.importo.setText("100,00");
			this.importo.setEditable(true);
			panel.getChildren().add(this.importo);
			
			panel.getChildren().add(new Label("Cambio ufficiale"));
			this.importo.setText("");
			this.importo.setEditable(false);
			panel.getChildren().add(this.importo);
			
			this.acquisto=new RadioButton("Acquisto");
			this.acquisto.setOnAction(arg0 -> {
				try {
					acquista(arg0);
				} catch (ParseException e) {
				controller.getUserInteractor().showMessage("Importo deve essere numerico");;
				}
			});
			this.acquisto.setVisible(false);
			panel.getChildren().add(this.acquisto);
			
			this.vendita=new RadioButton("Vendita");
			this.vendita.setOnAction(arg0 -> {
				try {
					vendita(arg0);
				} catch (ParseException e) {
					controller.getUserInteractor().showMessage("Importo deve essere numerico");
				}
			});
			this.vendita.setVisible(true);
			panel.getChildren().add(this.vendita);
			
		}
		this.setCenter(panel);
	}
	
	private void acquista(ActionEvent e) throws ParseException {
		StringBuilder sb=new StringBuilder();
		sb.append("Richiesta di compravendita di "+controller.getNomeCompletoValutaEstera(this.valuta.getValue())+"\n");
		for(CambiaValute c: controller.getListaCambiaValute()) {
			OptionalDouble result=c.acquisto(this.valuta.getValue(), CambiaValute.convertiInDouble(importo.getText()));
			if(result.isPresent())
				sb.append(c.getNomeAgenzia()+": "+this.valuta.getValue()+" EUR = "+result+"\n");
			else
				sb.append(c.getNomeAgenzia()+": non tratta la valuta estera richiesta\n");
		}
		sb.append("--------------------\n");
		this.output.setText(sb.toString());
		
		this.cambio.setText(CambiaValute.formatta(
					this.controller.getCambiUfficiali().get(this.valuta.getValue()).getTassoDiCambio()
					*
					CambiaValute.convertiInDouble(importo.getText()))
				);
	}
	
	private void vendita(ActionEvent e) throws ParseException {
		StringBuilder sb=new StringBuilder();
		sb.append("Richiesta di compravendita di "+controller.getNomeCompletoValutaEstera(this.valuta.getValue())+"\n");
		for(CambiaValute c: controller.getListaCambiaValute()) {
			OptionalDouble result=c.vendita(this.valuta.getValue(), CambiaValute.convertiInDouble(importo.getText()));
			if(result.isPresent())
				sb.append(c.getNomeAgenzia()+": "+this.valuta.getValue()+" EUR = "+result+"\n");
			else
				sb.append(c.getNomeAgenzia()+": non tratta la valuta estera richiesta\n");
		}
		sb.append("--------------------\n");
		this.output.setText(sb.toString());
		
		this.cambio.setText(CambiaValute.formatta(
					this.controller.getCambiUfficiali().get(this.valuta.getValue()).getTassoDiCambio()
					*
					CambiaValute.convertiInDouble(importo.getText()))
				);
	}
}
