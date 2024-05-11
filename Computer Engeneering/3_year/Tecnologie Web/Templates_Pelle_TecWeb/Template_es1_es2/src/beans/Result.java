package beans;

import java.util.ArrayList;
import java.util.List;

public class Result {
	
	private List<String> message; // Messaggi la loggare
	private List<Info> info; // Classe che rappresenta un dato complesso da condividere --> da riportare nel client
	
	public Result() {
		this.info = new ArrayList<>();
		this.message = new ArrayList<>();
	}

	

	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
	public void addMessage(String m) {
		this.message.add(m);
	}


	public List<Info> getInfo() {
		return info;
	}

	public void setInfo(List<Info> info) {
		this.info = info;
	}
	
	public void addInfo(Info i) {
		this.info.add(i);
	}
}
