package beans;

public class Soluzioni {

	private int campo;
	private int giorno;
	private int orario;
	
	public Soluzioni(int campo, int giorno, int orario) {
		this.campo=campo;
		this.giorno=giorno;
		this.orario=orario;
	}

	public int getCampo() {
		return campo;
	}

	public void setCampo(int campo) {
		this.campo = campo;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public int getOrario() {
		return orario;
	}

	public void setOrario(int orario) {
		this.orario = orario;
	}

}