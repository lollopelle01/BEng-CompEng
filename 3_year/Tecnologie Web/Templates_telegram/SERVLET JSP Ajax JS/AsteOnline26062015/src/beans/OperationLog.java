package beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class OperationLog {
	
	private List<Result> results = new ArrayList<Result>();
	
	public OperationLog() {
		
	}
	
	public void addResult(Result result) {
		removeOlds();
		synchronized (results) {
			results.add(result);
		}
	}
	
	public synchronized void removeOlds() {
		//results.removeIf(result -> LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() - result.getTime().atZone(ZoneId.systemDefault()).toEpochSecond() > 30*60);
		results.removeIf(result -> new Date().getTime() - result.getTime().getTime() > 30*60*1000);
	}
	
	public List<Result> getResults() {
		removeOlds();
		return results;
	}

}
