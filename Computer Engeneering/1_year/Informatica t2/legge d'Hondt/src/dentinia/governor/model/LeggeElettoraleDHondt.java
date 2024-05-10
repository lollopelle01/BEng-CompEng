package dentinia.governor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
public class LeggeElettoraleDHondt implements LeggeElettorale{

	@Override
	public RisultatoElezioni apply(Elezioni t) {
		long seggiDaAssegnare=t.getSeggiDaAssegnare();
		SortedSet<Partito> partiti=t.getPartiti();
		List<Quoziente> quozienti=new ArrayList<Quoziente>();
		RisultatoElezioni risultato=new RisultatoElezioni(partiti);
		
		for(Partito p: partiti) {
			for(int i=1; i<=seggiDaAssegnare; i++) {
				long tmp=t.getVoti(p)/i;
				quozienti.add(new Quoziente(p, tmp));
			}
			Collections.sort(quozienti);
		}
		
		List<Quoziente> filtrati=new ArrayList<>();
		for(int i=0; i<seggiDaAssegnare; i++) {
			filtrati.add(quozienti.get(i));
		}
		Collections.sort(filtrati);
		
		for(Quoziente q: filtrati) {
			long seggi=0;
			for(int i=0; i<filtrati.size(); i++) {
				if(q.getPartito().equals(filtrati.get(i).getPartito())) {
					seggi++;
				}
			}
			risultato.setSeggi(q.getPartito(), seggi);
		}
		return risultato;
	}

}
