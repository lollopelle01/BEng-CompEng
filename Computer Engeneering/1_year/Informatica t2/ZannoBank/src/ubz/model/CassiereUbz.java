package ubz.model;

import ubz.persistence.DotazioneLoader;

public class CassiereUbz extends Cassiere{
	private Politiche politiche;

	public CassiereUbz(DotazioneLoader loader) {
		super(loader);
		this.politiche=loader.getPolitiche();
	}
	
	protected int calcolaQuantit‡DiQuestoTaglio(Taglio t, int importo) {
		int quantit‡Richiesta=importo/t.getValore();
		int disponibilit‡=this.getDisponibilit‡Taglio(t);
		
		return Integer.min(quantit‡Richiesta, Integer.min(politiche.getQuantit‡(t), disponibilit‡));
	}

}
