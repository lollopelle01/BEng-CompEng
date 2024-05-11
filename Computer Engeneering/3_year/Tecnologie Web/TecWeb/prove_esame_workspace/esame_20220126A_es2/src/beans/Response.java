package beans;

public class Response {
	
	// Unica risposta per 3 possibili risposte

	private String type;
	private Request richiesta; // per inoltrare valore di un utente
	
	public Response() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Request getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(Request richiesta) {
		this.richiesta = richiesta;
	}

}