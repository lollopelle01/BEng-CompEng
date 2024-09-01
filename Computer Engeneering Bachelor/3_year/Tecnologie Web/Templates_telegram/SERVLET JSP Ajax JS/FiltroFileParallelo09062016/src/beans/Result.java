package beans;

import java.util.Date;

public class Result {
	
	private long count;
	private String filename = null;
	private char car;
	private Date time;
	
	public Result () {
		this.count = 0;
		time = new Date();
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setCar (char car) {
		this.car = car;
	}
	
	public long getCount () {
		return count;
	}
	
	public synchronized void addToCount(long num) {
		this.count += num;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public char getCar () {
		return this.car;
	}

}
