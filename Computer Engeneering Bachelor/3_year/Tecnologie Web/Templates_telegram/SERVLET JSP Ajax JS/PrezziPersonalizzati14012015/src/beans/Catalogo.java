package beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Catalogo {
	
	private Set<Prodotto> prodotti;
	
	public Catalogo() {
		prodotti = new HashSet<>();
		prodotti.add(new Prodotto("1", "Prodotto1", "Desc1", 10, 50));
		prodotti.add(new Prodotto("2", "Prodotto2", "Desc2", 1, 23));
		prodotti.add(new Prodotto("3", "Prodotto3", "Desc3", 100, 5));
	}

	public Set<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(Set<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	
	public Prodotto getById(String id) {
		for (Prodotto p : prodotti) {
			if (p.getId().equals(id))
				return p;
		}
		return null;
	}
	
	public synchronized boolean checkAndBuy(Map<String, Integer> carrello) {
		for (String id : carrello.keySet()) {
			if (getById(id).getNumDisponibili() < carrello.get(id))
				return false;
		}
		for (String id : carrello.keySet()) {
			getById(id).vendi(carrello.get(id));
		}
		return true;
	}

	

}
