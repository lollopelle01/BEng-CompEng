package Beans;

public class Drink {
	
	private int quantity;
	private double price;
	private double tot;
	
	public Drink(int quantity, double price) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.tot = quantity * price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTot() {
		return tot;
	}

}
