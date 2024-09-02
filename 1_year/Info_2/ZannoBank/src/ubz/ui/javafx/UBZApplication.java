package ubz.ui.javafx;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ubz.model.CassiereUbz;
import ubz.persistence.DotazioneLoader;
import ubz.persistence.MyDotazioneLoader;
import ubz.ui.controller.Controller;
import ubz.ui.controller.DialogManager;

public class UBZApplication extends Application implements DialogManager {

	private Controller controller;

	public void start(Stage stage) {
		controller = createController();
		if (controller == null)
			return;
		stage.setTitle("Unione Banche di Zannonia");
		ZannoBankPane root = new ZannoBankPane(controller);

		Scene scene = new Scene(root, 600, 300, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	protected Controller createController() {
		DotazioneLoader loader = new MyDotazioneLoader();		
		try {
			loader.load(new FileInputStream("DotazioneIniziale.dat"));
			return new Controller(new CassiereUbz(loader), this);
		} catch (Exception e) {
			alert("Errore di I/O", "Errore nel caricamento file binario", e.getMessage());
			return null;
		}
	}

	public void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
