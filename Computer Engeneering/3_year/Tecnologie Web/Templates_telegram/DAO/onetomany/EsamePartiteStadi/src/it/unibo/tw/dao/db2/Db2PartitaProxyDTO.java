package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.PartitaDTO;
import it.unibo.tw.dao.StadioDTO;

public class Db2PartitaProxyDTO extends PartitaDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2PartitaProxyDTO() {
		super();
	}
	
	@Override
	public StadioDTO getStadio() {
		if (!alreadyLoaded) {
			this.setStadio(new Db2StadioDAO().readByIdPartitaLazy(getCodicePartita()));
		}
		return super.getStadio();
	}
	
	@Override
	public void setStadio(StadioDTO stadio) {
		super.setStadio(stadio);
		alreadyLoaded = true;
	}

}
