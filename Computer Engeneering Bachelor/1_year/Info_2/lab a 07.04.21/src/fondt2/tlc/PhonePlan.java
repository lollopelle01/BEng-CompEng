package fondt2.tlc;

public class PhonePlan {
	private String name;
	private Rate[] rates;
	
	public String getName() {
		return name;
	}

	public Rate[] getRates() {
		return rates;
	}
	public PhonePlan(String name, Rate[] rates) {
		this.name=name;
		this.rates=rates;
	}
	
}
