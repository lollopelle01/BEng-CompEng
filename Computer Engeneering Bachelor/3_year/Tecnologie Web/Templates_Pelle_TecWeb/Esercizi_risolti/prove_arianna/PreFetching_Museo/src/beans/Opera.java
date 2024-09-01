package beans;

import java.io.File;
import java.io.Serializable;

public class Opera implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private File gif;
	private String nome;
	private String info;
	
	public Opera(File f, String nome, String info) {
		this.gif = f;
		this.nome = nome;
		this.info = info;
	}

	public File getGif() {
		return gif;
	}

	public void setGif(File gif) {
		this.gif = gif;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
