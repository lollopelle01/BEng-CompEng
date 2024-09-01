package beans;

public class Response {
	
	String nome;
	String testo;
	boolean valid;
	
	public Response() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Request [nome=" + nome + "\n\ntesto=" + testo + "\n\nvalid=" + valid + "]";
	}
	

}