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
	
	public double getCallCost(PhoneCall call) {
		double result=-1;
		for(Rate r: rates) {
			if(r.isApplicableTo(call.getDestNumber())) {
				result=r.getCallCost(call);
			}
		}
		return result;
	}
	
	public boolean isValid() {
		for(Rate r: rates) {
			if(!r.isValid())
				return false;
		}
		return true;
	}
}
