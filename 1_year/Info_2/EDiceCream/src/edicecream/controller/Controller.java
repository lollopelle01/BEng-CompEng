package edicecream.controller;

import java.util.List;
import java.util.Set;

public interface Controller {
	Set<String> getFlavors();
	Set<String> getKindNames();
	boolean addIceCream(String kind, List<String> flavors);
	void printReport();
	int getMaxFlavors(String flavor);
	String getIceCreamStatus();
}
