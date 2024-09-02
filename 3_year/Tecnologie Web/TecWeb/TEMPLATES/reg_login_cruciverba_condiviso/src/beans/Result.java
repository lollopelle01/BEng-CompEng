package beans;

public class Result {
	private String message;
	private String message_colonna;
	
	public Result() {
		this.message = "";
		this.message_colonna = "";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage_colonna() {
		return message_colonna;
	}

	public void setMessage_colonna(String message_colonna) {
		this.message_colonna = message_colonna;
	}
	
	
}
