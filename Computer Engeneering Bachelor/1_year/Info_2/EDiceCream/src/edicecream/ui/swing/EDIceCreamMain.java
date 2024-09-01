package edicecream.ui.swing;
	
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import edicecream.controller.Controller;
import edicecream.controller.MyController;
import edicecream.model.IceCreamShop;
import edicecream.persistence.BadFileFormatException;
import edicecream.persistence.IceCreamKindsRepository;
import edicecream.persistence.IceCreamRepository;
import edicecream.persistence.MyIceCreamKindsRepository;
import edicecream.persistence.MyIceCreamRepository;
import edicecream.persistence.MyStockWriter;
import edicecream.persistence.StockWriter;


public class EDIceCreamMain {
	public static void main(String[] args) {

		try (Reader readerKind = new FileReader("Kinds.txt"); Reader readerQuant = new FileReader("IceCreamQuantity.txt");) {
			IceCreamKindsRepository kindRepo = new MyIceCreamKindsRepository(readerKind);
			IceCreamRepository iceRepo = new MyIceCreamRepository(readerQuant);
			IceCreamShop shop = new IceCreamShop();
			StockWriter stockWriter = new MyStockWriter();
			Controller controller = new MyController(kindRepo, iceRepo, shop, stockWriter);
			IceCreamFrame frame = new IceCreamFrame(controller);
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			e.printStackTrace();
		}
	}
}
