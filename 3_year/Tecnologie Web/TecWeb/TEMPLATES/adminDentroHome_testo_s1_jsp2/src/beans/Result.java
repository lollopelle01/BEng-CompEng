package beans;

public class Result {
	private String message;
	private String text;
	private int counter;
	
	public Result() {
		this.message = "";
		this.text = "";
		this.counter = 0;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
}
