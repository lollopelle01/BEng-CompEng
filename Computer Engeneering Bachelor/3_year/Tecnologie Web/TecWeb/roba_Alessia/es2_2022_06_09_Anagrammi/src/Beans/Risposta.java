package Beans;

public class Risposta {

	private String servlet;
	private String[] anagrammi = new String[10];
	
	public Risposta(String servlet, String[] anagrammi) {
		super();
		this.servlet = servlet;
		this.anagrammi = anagrammi;
	}

	public String getServlet() {
		return servlet;
	}

	public void setServlet(String servlet) {
		this.servlet = servlet;
	}

	public String[] getAnagrammi() {
		return anagrammi;
	}

	public void setAnagrammi(String[] anagrammi) {
		this.anagrammi = anagrammi;
	}
	
}
