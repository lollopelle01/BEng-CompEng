package beans;

import java.util.ArrayList;
import java.util.List;

public class RisultatiPassati {
	
	private List<Catalogo> cataloghi = new ArrayList<>();

	public List<Catalogo> getRisultati() {
		return cataloghi;
	}

	public synchronized void addRisultato(Catalogo risultato) {
		if (cataloghi.size() >= 3) {
			cataloghi.remove(0);
		}
		cataloghi.add(risultato);
	}
	
	

}
