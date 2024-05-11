package beans;

public class Request {
	
	String nome;
	String testo;
	
	public Request() {

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

	@Override
	public String toString() {
		return "Request [nome=" + nome + "\n\ntesto=" + testo + "]";
	}
	

}
