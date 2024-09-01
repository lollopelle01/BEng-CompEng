package beans;

public class ErrorMessage extends GenericMessage<String> {
	private static final long serialVersionUID = 1L;

	public ErrorMessage() {
		setType("");
		setMessage("");
	}

	public ErrorMessage(String message) {
		setType("error");
		setMessage(message);
	}

	@Override
	public String toString() {
		return "MyError [type=" + getType() + ", message=" + getMessage() + "]";
	}
}
