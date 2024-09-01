package edicecream.ui.javafx;
	
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import edicecream.persistence.*;
import edicecream.controller.*;
import edicecream.model.IceCreamShop;


public class EDIceCreamApplication extends Application {
	private Controller controller;

	@Override
	public void start(Stage stage) {
		controller = createController();
		if (controller == null)
			return;
		stage.setTitle("ED Creams & Dreams");
		IceCreamPane root = new IceCreamPane(controller);
		Scene scene = new Scene(root, 800, 300, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	protected Controller createController() {

		try (Reader readerKind = new FileReader("Kinds.txt"); Reader readerQuant = new FileReader("IceCreamQuantity.txt");) {
			IceCreamKindsRepository kindRepo = new MyIceCreamKindsRepository(readerKind);
			IceCreamRepository iceRepo = new MyIceCreamRepository(readerQuant);
			IceCreamShop shop = new IceCreamShop();
			StockWriter stockWriter = new MyStockWriter();
			return new MyController(kindRepo, iceRepo, shop, stockWriter);
		} catch (IOException e) {
			alert("Errore di I/O", "Impossibile aprire uno dei file", "Errore nell'apertura del reader o del writer");
			return null;
		} catch (BadFileFormatException e) {
			alert("Errore di I/O", "Errore di formato nel file", "Impossibile effettuare la lettura");
			return null;
		}
	}

	public static void alert(String title, String headerMessage, String contentMessage) {
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
