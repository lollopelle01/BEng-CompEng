package edicecream.ui.javafx;

import edicecream.controller.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class IceCreamPane extends BorderPane {
	private Controller controller;
	private ComboBox<String> kind;
	private FlavorPane flavorPane;
	private Button conferma, stampa;
	private TextArea output;

	public IceCreamPane(Controller controller) {
		this.controller=controller;
		initGui();
	}

	private void initGui() {
		Background back1 = new Background(new BackgroundFill(Color.LAVENDER, new CornerRadii(0, false), new Insets (0, 0 ,0, 0))); 
//		Background back2 = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(0, false), new Insets (0, 0 ,0, 0)));
//		Background back3 = new Background(new BackgroundFill(Color.LIGHTCYAN, new CornerRadii(0, false), new Insets (0, 0 ,0, 0))); 
//		Background back4 = new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, new CornerRadii(0, false), new Insets (0, 0 ,0, 0)));

		setBackground(back1);

		VBox left = new VBox();
		{
			left.setAlignment(Pos.CENTER);
			left.setSpacing(10);
			left.getChildren().add(new Label("Tipo:"));
			kind = new ComboBox<String>(FXCollections.observableArrayList(controller.getKindNames()));
			kind.setPromptText("Scegli il tipo");

			kind.valueProperty().addListener((ov, t1, t2) -> {
				setFlavorMaxNumber(controller.getMaxFlavors(kind.getValue()));
			});
			left.getChildren().add(kind);

			flavorPane = new FlavorPane(controller.getFlavors());
			flavorPane.setPadding(new Insets(4,4,4,4));
			left.getChildren().add(flavorPane);

			HBox bottoni = new HBox();
			bottoni.setSpacing(10);
			bottoni.setPadding(new Insets(4,4,4,4));

			conferma = new Button("Conferma");
			conferma.setOnAction(this::myConfirmHandle);
			conferma.setPrefSize(250, 30);
			bottoni.getChildren().add(conferma);

			stampa = new Button("Stampa");
			stampa.setOnAction(event ->  controller.printReport());
			stampa.setPrefSize(250, 30);
			bottoni.getChildren().add(stampa);
			
			left.getChildren().add(bottoni);
		}

		VBox right = new VBox();
		{
			right.setAlignment(Pos.CENTER);
			right.setSpacing(10);
			right.setPadding(new Insets(0, 20, 10, 20));
            right.getChildren().add(new Label("Situazione Attuale"));
			output = new TextArea();
			output.setEditable(false);
			right.getChildren().add(output);
		}

		this.setLeft(left);
		this.setCenter(right);

	}

	private void setFlavorMaxNumber(int flavorNumber) {
		flavorPane.setMaxSelected(flavorNumber);
		
	}
	
	private void myConfirmHandle(ActionEvent e) 
	{
		boolean result = controller.addIceCream(kind.getValue(), flavorPane.getSelected());
		if (result)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Complimenti");
			alert.setHeaderText("Fantastica Scelta");
			alert.setContentText("Complimenti per la scelta del gelato");
			alert.showAndWait();	
			output.setText(controller.getIceCreamStatus());
			kind.setValue(null);
			flavorPane.reset();
			
		}
		else{
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attenzione");
			alert.setHeaderText("Siamo spiacenti");
			alert.setContentText("Uno dei gusti da te selezionato non è disponibile");
			alert.showAndWait();
		}
	}
}
