package edicecream.controller.test;

import java.util.HashMap;
import java.util.Set;

import edicecream.persistence.IceCreamRepository;

public class IceCreamRepositoryMock implements IceCreamRepository {

	private HashMap<String, Integer> stock;

	public IceCreamRepositoryMock() {
		stock = new HashMap<>();
		stock.put("cioccolato", 2000);
		stock.put("crema", 1500);
		stock.put("stracciatella", 3000);
	}

	@Override
	public Set<String> getFlavors() {
		return stock.keySet();
	}

	@Override
	public boolean removeQuantity(String flavor, int qty) {
		Integer current = stock.get(flavor);
		if (current == null || current.intValue() < qty) {
			return false;
		}
		stock.put(flavor, current - qty);
		return true;
	}
	
	@Override
	public Integer getAvailableQuantity(String flavor) {
		return stock.get(flavor);
	}
}
