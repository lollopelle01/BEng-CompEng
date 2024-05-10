package edicecream.ui.swing;
	
import edicecream.controller.Controller;
import edicecream.controller.MyController;
import edicecream.controller.test.IceCreamKindsRepositoryMock;
import edicecream.controller.test.IceCreamRepositoryMock;
import edicecream.model.IceCreamShop;
import edicecream.persistence.IceCreamKindsRepository;
import edicecream.persistence.IceCreamRepository;
import edicecream.persistence.MyStockWriter;
import edicecream.persistence.StockWriter;


public class GUITest {
	public static void main(String[] args) {
		IceCreamKindsRepository kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepository iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		StockWriter stockWriter = new MyStockWriter();
		Controller controller = new MyController(kindRepo, iceRepo, shop, stockWriter);
		IceCreamFrame frame = new IceCreamFrame(controller);
		frame.setVisible(true);
	}
}
