package edicecream.persistence;

import java.util.Set;

public interface IceCreamRepository {
	Set<String> getFlavors();
	boolean removeQuantity(String flavor, int qty);
	Integer getAvailableQuantity(String flavor);
}
