package it.unibo.tw.dao.db2;

import java.util.Set;

import it.unibo.tw.dao.PartitaDTO;
import it.unibo.tw.dao.StadioDTO;

public class Db2StadioProxyDTO extends StadioDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2StadioProxyDTO() {
		super();
	}
	
	@Override
	public Set<PartitaDTO> getPartite() {
		if (!alreadyLoaded) {
			this.setPartite(new Db2PartitaDAO().getPartiteByStadioId(getCodice()));
		}
		return super.getPartite();
	}
	
	@Override
	public void setPartite(Set<PartitaDTO> partite) {
		super.setPartite(partite);
		alreadyLoaded = true;
	}

}
