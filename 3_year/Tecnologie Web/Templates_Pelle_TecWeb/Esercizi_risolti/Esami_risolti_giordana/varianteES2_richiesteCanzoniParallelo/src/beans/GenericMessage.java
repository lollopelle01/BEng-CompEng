package beans;

import java.io.Serializable;

public class GenericMessage<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String type;
	private T message;
	
	public GenericMessage() {
		type = "";
	}
	
	public GenericMessage(String type, T message) {
		this.type = type;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	public Boolean typeEquals(String type) {
		return this.type.equals(type);
	}
	
	@Override
	public String toString() {
		return "GenericMessage [type=" + type + ", message=" + message + "]";
	}
}