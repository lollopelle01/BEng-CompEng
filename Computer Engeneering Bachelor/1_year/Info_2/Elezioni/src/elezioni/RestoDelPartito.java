package elezioni;

public class RestoDelPartito {
	private String partito;
	private long resto;
	
	public void azzeraResto() {
		this.resto=0;
	}
	public String getPartito() {
		return this.partito;
	}
	public long getResto() {
		return this.resto;
	}
	public RestoDelPartito(String partito, long resto) {
		this.partito=partito;
		this.resto=resto;
	}
	
	@Override
	public String toString() {
		return "Il resto di "+this.partito+" e' : "+this.resto;
	}
	
}
