package beans;

import java.time.LocalDateTime;

public class CounterRichieste {
	private LocalDateTime timeLastRequest;
	private int counter;
	
	public CounterRichieste() {
		this.counter = 0;
		this.timeLastRequest = LocalDateTime.now();
	}
	
	public LocalDateTime getTimeLastRequest() {
		return timeLastRequest;
	}
	
	public void setTimeLastRequest(LocalDateTime timeLastRequest) {
		this.timeLastRequest = timeLastRequest;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void updateCounter() {
		this.counter = this.counter+1;
		this.timeLastRequest = LocalDateTime.now();
	}
	
	public void initialize() {
		this.counter = 0;
		this.timeLastRequest = LocalDateTime.now();
	}
}
