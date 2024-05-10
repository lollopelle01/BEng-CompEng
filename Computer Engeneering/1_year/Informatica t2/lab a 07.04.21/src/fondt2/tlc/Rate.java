package fondt2.tlc;

public class Rate {
	private Band[] bands;
	private int intervalMillis;
	private String name;
	private String numberRoot;
	private double startCallCost;
	
	public Band[] getBands() {
		return bands;
	}
	public String getName() {
		return name;
	}	
	public Rate(String name, Band[] bands, int intervalMillis, double startCallCost, String numberRoot) {
		this.name=name;
		this.bands=bands;
		this.intervalMillis=intervalMillis;
		this.startCallCost=startCallCost;
		this.numberRoot=numberRoot;
	}
	
	public boolean isApplicableTo(String destinationNumber) {
		boolean r1=false;
		if(destinationNumber.startsWith(numberRoot))
			r1=true;
		return r1;
	}
	
}
