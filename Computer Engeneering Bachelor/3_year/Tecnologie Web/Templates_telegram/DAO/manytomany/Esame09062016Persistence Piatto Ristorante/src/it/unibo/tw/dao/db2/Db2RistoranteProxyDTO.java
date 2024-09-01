package it.unibo.tw.dao.db2;

import java.util.Set;

import it.unibo.tw.dao.PiattoDTO;
import it.unibo.tw.dao.RistoranteDTO;

public class Db2RistoranteProxyDTO extends RistoranteDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alreadyLoaded = false;
	
	
	public Db2RistoranteProxyDTO() {
		super();
	}
	
	@Override
	public Set<PiattoDTO> getPiatti() {
		if (!alreadyLoaded) {
			this.setPiatti(new Db2MappingDAO().getPiattiByRistoranteId(getId()));
		}
		return super.getPiatti();
	}
	
	@Override
	public void setPiatti(Set<PiattoDTO> piatti) {
		super.setPiatti(piatti);
		alreadyLoaded = true;
	}

}
