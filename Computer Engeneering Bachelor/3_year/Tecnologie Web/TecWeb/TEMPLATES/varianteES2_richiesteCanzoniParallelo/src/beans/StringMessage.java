package beans;

public class StringMessage extends GenericMessage<String> {
	private static final long serialVersionUID = 1L;

	public StringMessage(String type, String message) {
		setType(type);
		setMessage(message);
	}

	public StringMessage() {
		setType("");
		setMessage("");
	}

	@Override
	public String toString() {
		return "StringMessage [type=" + getType() + ", message=" + getMessage() + "]";
	}
}
