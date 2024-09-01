package it.unibo.tw.dao.db2;

import it.unibo.tw.dao.ProiezioneDTO;
import it.unibo.tw.dao.SalaDTO;

public class Db2ProiezioneProxyDTO extends ProiezioneDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2ProiezioneProxyDTO() {
		super();
	}
	
	@Override
	public SalaDTO getSala() {
		if (!alreadyLoaded) {
			this.setSala(new Db2SalaDAO().readByIdProiezioneLazy(getId()));
		}
		return super.getSala();
	}
	
	@Override
	public void setSala(SalaDTO sala) {
		super.setSala(sala);
		alreadyLoaded = true;
	}

}
