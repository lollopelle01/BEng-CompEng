package edicecream.controller.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import edicecream.controller.MyController;
import edicecream.model.IceCreamKind;
import edicecream.model.IceCreamShop;
import edicecream.persistence.MyStockWriter;

public class ControllerTest {
	@Test
	public void controllerTestAddIceCream() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		boolean expected = true;

		while (expected) {
			for (String kindName : kindRepo.getKindNames()) {
				IceCreamKind kind = kindRepo.getIceCreamKind(kindName);

				ArrayList<String> flavor = new ArrayList<>();
				Map<String,Integer> flavorStock = new HashMap<>();
				Iterator<String> ite = controller.getFlavors().iterator();
				expected = true;
				int flavorWeight = kind.getWeight() / kind.getMaxFlavors();

				for (int i=0; ite.hasNext() && i<controller.getMaxFlavors(kindName); i++) {
					String flName = ite.next();
					flavor.add(flName);
					int qty = iceRepo.getAvailableQuantity(flName);
					if (qty < flavorWeight) {
						expected = false;
					}
					flavorStock.put(flName, qty);
				}
				float revenue = shop.getTotalIncome();

				boolean ret = controller.addIceCream(kindName, flavor);

				assertEquals(expected, ret);
				if (ret) {
					for (String flName : flavor) {
						assertEquals(flavorStock.get(flName) - flavorWeight, iceRepo.getAvailableQuantity(flName).intValue());
					}
					assertEquals(revenue + kind.getPrice(), shop.getTotalIncome(), 1.00);
				} else {
					for (String flName : flavor) {
						assertEquals(flavorStock.get(flName), iceRepo.getAvailableQuantity(flName));
					}
					assertEquals(revenue, shop.getTotalIncome(), 1.00);
				}
			}
		}
	}

	@Test
	public void controllerTestAddIceCreamMenoGusti() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		boolean expected = true;

		while (expected) {
			for (String kindName : kindRepo.getKindNames()) {
				IceCreamKind kind = kindRepo.getIceCreamKind(kindName);

				ArrayList<String> flavor = new ArrayList<>();
				Map<String,Integer> flavorStock = new HashMap<>();
				Iterator<String> ite = controller.getFlavors().iterator();
				expected = true;
				int flavorWeight = kind.getWeight();

				String flName = ite.next();
				flavor.add(flName);
				int qty = iceRepo.getAvailableQuantity(flName);
				if (qty < flavorWeight) {
					expected = false;
				}
				flavorStock.put(flName, qty);

				float revenue = shop.getTotalIncome();

				boolean ret = controller.addIceCream(kindName, flavor);

				assertEquals(expected, ret);
				if (ret) {
					assertEquals(flavorStock.get(flName) - flavorWeight, iceRepo.getAvailableQuantity(flName).intValue());
					assertEquals(revenue + kind.getPrice(), shop.getTotalIncome(), 1.00);
				} else {
					assertEquals(flavorStock.get(flName), iceRepo.getAvailableQuantity(flName));
					assertEquals(revenue, shop.getTotalIncome(), 1.00);
				}
			}
		}
	}

	@Test
	public void controllerTestGetFlavorNumber() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		for (String kindName : kindRepo.getKindNames()) {
			IceCreamKind kind = kindRepo.getIceCreamKind(kindName);
			assertEquals(kind.getMaxFlavors(), controller.getMaxFlavors(kindName));
		}
	}

	@Test
	public void controllerTestGetFlavors() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		assertEquals(iceRepo.getFlavors(), controller.getFlavors());
	}
	
	@Test
	public void controllerTestGetKinds() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		assertEquals(kindRepo.getKindNames(), controller.getKindNames());
	}
	
	@Test
	public void controllerTestGetIceCreamStatus() {
		IceCreamKindsRepositoryMock kindRepo = new IceCreamKindsRepositoryMock();
		IceCreamRepositoryMock iceRepo = new IceCreamRepositoryMock();
		IceCreamShop shop = new IceCreamShop();
		MyController controller = new MyController(kindRepo, iceRepo, shop, new MyStockWriter());
		
		String expected =
				"Totale gelati venduti: 0\n"+
				"Venduti 0gr di cioccolato\n"+
				"Venduti 0gr di crema\n"+
				"Venduti 0gr di stracciatella\n"+
				"Totale incasso: 0.0 euro\n";

		assertEquals(expected, controller.getIceCreamStatus());
	}
}
