package beans;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private String message;
	private List<Soluzioni> soluzioni;
	
	public Result() {
		this.message = "";
		this.soluzioni = new ArrayList<>();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Soluzioni> getSoluzioni() {
		return soluzioni;
	}

	public void setSoluzioni(List<Soluzioni> soluzioni) {
		this.soluzioni = soluzioni;
	}

	
	
	
	
}
