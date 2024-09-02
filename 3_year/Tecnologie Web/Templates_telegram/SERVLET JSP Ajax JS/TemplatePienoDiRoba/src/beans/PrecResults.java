package beans;

import java.util.ArrayList;
import java.util.List;

public class PrecResults {
	
	private List<Result> results = new ArrayList<Result>();
	
	public PrecResults() {
		
	}
	
	public void addResult (Result result) {
		if (results.size() >= 3) {
			results.remove(0);	
		}
		
		results.add(result);
	}
	
	public List<Result> getResults() {
		return results;
	}

}
