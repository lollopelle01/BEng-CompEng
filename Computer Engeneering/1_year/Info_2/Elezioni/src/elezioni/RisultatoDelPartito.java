package elezioni;

public class RisultatoDelPartito {
	private String partito;
	private long seggi;
	private long voti;
	
	public String getNome() {
		return this.partito;
	}
	public long getSeggi() {
		return this.seggi;
	}
	public long getVoti() {
		return this.voti;
	}
	public long incVoti() {
		return this.voti++;
	}
	public void setSeggi(long seggi) {
		this.seggi=seggi;
	}
	public RisultatoDelPartito(String partito, long seggi, long voti) {
		this.partito=partito;
		this.seggi=seggi;
		this.voti=voti;
	}
	
	@Override
	public String toString() {
		return this.partito+" ha ottenuto "+this.voti+" voti e "+this.seggi+" seggi";
	}
}
