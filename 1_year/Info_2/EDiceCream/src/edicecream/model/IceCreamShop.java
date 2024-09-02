package edicecream.model;

import java.util.ArrayList;

public class IceCreamShop {
private ArrayList<IceCream> lista; 
	
	public void addIceCream(IceCream iceCream) {
		lista.add(iceCream);
	}
	
	public int getIceCreamCount() {
		return lista.size();
	}
	
	public float getTotalIncome() {
		float result=0;
		for(IceCream i: lista) {
			result=result+i.getKind().getPrice();
		}
		return result;
	}
	
	public int getTotalFlavorConsumption(String flavor) {
		int result=0;
		for(IceCream i: lista) {
			if(i.hasFlavor(flavor)) {
				result+=i.getFlavorWeight();
			}
		}
		return result;
	}
	
	public IceCreamShop() {
		this.lista=new ArrayList<IceCream>();
	}
}
