package edicecream.model;

import java.util.ArrayList;
import java.util.List;

public class IceCream {
	private IceCreamKind kind;
	private List<String> flavors;

	public IceCream(IceCreamKind kind, List<String> flavors) {
		this.flavors = new ArrayList<>(flavors);
		this.kind = kind;
	}

	public IceCreamKind getKind() {
		return kind;
	}

	public boolean hasFlavor(String flavor) {
		return flavors.contains(flavor);
	}
	
	public int getFlavorWeight() {
		return kind.getWeight() / flavors.size();
	}
}
