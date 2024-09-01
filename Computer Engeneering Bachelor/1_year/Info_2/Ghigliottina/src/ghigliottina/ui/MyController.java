package ghigliottina.ui;

import java.util.List;
import java.util.Random;

import ghigliottina.model.Ghigliottina;

public class MyController implements Controller{
	private List<Ghigliottina> ghigliottine;
	
	public MyController(List<Ghigliottina> ghigliottine) {
		super();
		this.ghigliottine = ghigliottine;
	}

	@Override
	public Ghigliottina sorteggiaGhigliottina() {
		Random r=new Random();
		return this.ghigliottine.get(r.nextInt(this.ghigliottine.size()));
	}

	@Override
	public List<Ghigliottina> listaGhigliottine() {
		return this.ghigliottine;
	}

}
