package edicecream.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edicecream.model.IceCream;
import edicecream.model.IceCreamKind;
import edicecream.model.IceCreamShop;

public class IceCreamShopTest {
	@Test
	public void testShop0() {
		IceCreamShop shop = new IceCreamShop();
		assertEquals(0, shop.getIceCreamCount());
		assertEquals(0, shop.getTotalFlavorConsumption("cioccolato"));
		assertEquals(0f, shop.getTotalIncome(), 0.01);
	}
	
	@Test
	public void testShop1() {
		IceCreamShop shop = new IceCreamShop();
		ArrayList<String> flavors = new ArrayList<>();
		flavors.add("cioccolato");
		shop.addIceCream(new IceCream(new IceCreamKind("Test", 1.0f, 1, 40), flavors));
		assertEquals(1, shop.getIceCreamCount());
		assertEquals(40, shop.getTotalFlavorConsumption("cioccolato"));
		assertEquals(1.0f, shop.getTotalIncome(), 0.01);
	}

	@Test
	public void testShop2() {
		IceCreamShop shop = new IceCreamShop();

		ArrayList<String> flavors = new ArrayList<>();
		flavors.add("cioccolato");
		flavors.add("crema");
		shop.addIceCream(new IceCream(new IceCreamKind("Test", 1.0f, 2, 80), flavors));

		flavors = new ArrayList<>();
		flavors.add("cioccolato");
		shop.addIceCream(new IceCream(new IceCreamKind("Test", 2.0f, 1, 80), flavors));
	
		assertEquals(2, shop.getIceCreamCount());
		assertEquals(120, shop.getTotalFlavorConsumption("cioccolato"));
		assertEquals(40, shop.getTotalFlavorConsumption("crema"));
		assertEquals(3.0f, shop.getTotalIncome(), 0.01);
	}

}
