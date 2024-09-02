package oroscopo.controller;

import java.io.IOException;

import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public class MyController extends AbstractController{
	private StrategiaSelezione strategiaSelezione;
	
	public MyController(OroscopoRepository repository, StrategiaSelezione strategiaSelezione) throws IOException {
		super(repository);
		if(strategiaSelezione==null) {
			throw new IllegalArgumentException();
		}
		this.strategiaSelezione=strategiaSelezione;
	}
	
	@Override
	public SegnoZodiacale[] getSegni() {
		return SegnoZodiacale.values();
	}

	@Override
	public Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin) {
		int fortunaTot;
		Oroscopo[] oroscopo=new Oroscopo[12];
		do {
			fortunaTot=0;
			for(int i=0; i<12; i++) {
				oroscopo[i]=generaOroscopoCasuale(segno);
				fortunaTot+=oroscopo[i].getFortuna();
			}
		}while(fortunaTot<=fortunaMin);
		return oroscopo;
	}

	@Override
	public Oroscopo generaOroscopoCasuale(SegnoZodiacale segno) {
		return new OroscopoMensile(segno, 
				this.strategiaSelezione.seleziona(super.getRepository().getPrevisioni("Amore"), segno),
				this.strategiaSelezione.seleziona(super.getRepository().getPrevisioni("Lavoro"), segno),
				this.strategiaSelezione.seleziona(super.getRepository().getPrevisioni("Salute"), segno));
	}

}
