package it.unibo.tw.dao.db2;

import java.util.Set;

import it.unibo.tw.dao.CinefiloDTO;
import it.unibo.tw.dao.ProiezioneDTO;

public class Db2CinefiloProxyDTO extends CinefiloDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2CinefiloProxyDTO() {
		super();
	}
	
	@Override
	public Set<ProiezioneDTO> getProiezioni() {
		if (!alreadyLoaded) {
			this.setProiezioni(new Db2MappingDAO().getProiezioniByCinefiloId(getId()));
		}
		return super.getProiezioni();
	}
	
	@Override
	public void setProiezioni(Set<ProiezioneDTO> proiezioni) {
		super.setProiezioni(proiezioni);
		alreadyLoaded = true;
	}
}
