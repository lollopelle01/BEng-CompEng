import java.io.Serializable;

public class Esito implements Serializable{
	private String file;
	private int righeInFile;
	
	public Esito(String fileName, int righe) {
		this.file = fileName;
		this.righeInFile = righe;
	}
	
	public int getRighe() {
		return this.righeInFile;
	}
	
	public String getFile() {
		return this.file;
	}
}
