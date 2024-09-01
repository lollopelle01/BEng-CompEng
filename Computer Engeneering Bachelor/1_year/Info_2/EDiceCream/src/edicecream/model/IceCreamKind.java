package edicecream.model;

public class IceCreamKind {
	private String name;
	private float price;
	private int maxFlavors;
	private int weight;
	
	public IceCreamKind(String name, float price, int maxFlavors, int weight){
		this.name=name;
		this.price=price;
		this.maxFlavors=maxFlavors;
		this.weight=weight;
	}

	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}
	public int getMaxFlavors() {
		return maxFlavors;
	}
	public int getWeight() {
		return weight;
	}
}
