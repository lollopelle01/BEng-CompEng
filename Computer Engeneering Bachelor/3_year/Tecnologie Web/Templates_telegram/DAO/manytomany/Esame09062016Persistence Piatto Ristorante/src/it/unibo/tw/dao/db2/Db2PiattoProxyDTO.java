package it.unibo.tw.dao.db2;

import java.util.Set;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;

public class Db2PiattoProxyDTO extends PiattoDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2PiattoProxyDTO() {
		super();
	}
	
	@Override
	public Set<RistoranteDTO> getRistoranti() {
		if (!alreadyLoaded) {
			this.setRistoranti(new Db2MappingDAO().getRistorantiByPiattoId(getId()));
		}
		return super.getRistoranti();
	}
	
	@Override
	public void setRistoranti(Set<RistoranteDTO> ristoranti) {
		super.setRistoranti(ristoranti);
		alreadyLoaded = true;
	}
}
