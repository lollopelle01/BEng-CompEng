package edicecream.ui.javafx;
	
import edicecream.controller.Controller;
import edicecream.controller.MyController;
import edicecream.controller.test.IceCreamKindsRepositoryMock;
import edicecream.controller.test.IceCreamRepositoryMock;
import edicecream.model.IceCreamShop;
import edicecream.persistence.IceCreamKindsRepository;
import edicecream.persistence.IceCreamRepository;
import edicecream.persistence.MyStockWriter;
import edicecream.persistence.StockWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class EDIceCreamApplicationMock extends Application {
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
		IceCreamKindsRepository kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepository iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		StockWriter stockWriter = new MyStockWriter();
		return new MyController(kindRepo, iceRepo, shop, stockWriter);
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
