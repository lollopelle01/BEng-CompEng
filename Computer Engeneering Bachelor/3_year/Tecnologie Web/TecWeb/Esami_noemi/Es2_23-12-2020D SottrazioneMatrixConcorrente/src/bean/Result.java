package bean;

public class Result {

	private boolean isPositive;  //true se è matrice con tutti gli elementi positivi
	private long time;
	public boolean isPositive() {
		return isPositive;
	}
	public void setSuccess(boolean isPositive) {
		this.isPositive = isPositive;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Result(boolean isPositive, long time) {
		super();
		this.isPositive = isPositive;
		this.time = time;
	}


}
