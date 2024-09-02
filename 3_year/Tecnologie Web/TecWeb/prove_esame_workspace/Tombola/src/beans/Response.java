package beans;

public class Response {
	
	private boolean autenticato;
	private boolean ok;
	
	public Response() {

	}

	public boolean isOk() {
		return ok;
	}



	public void setOk(boolean ok) {
		this.ok = ok;
	}



	public boolean isAutenticato() {
		return autenticato;
	}

	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	
	

}