package beans;

import java.io.Serializable;

public class FileResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String canzone;
	private long length;
	private String formato;
	private String binario;
	
    
	// --- constructor ----------
	public FileResult(String c, long l, String f, String b) {
        this.canzone = c;
        this.length = l;
        this.formato = f;
        this.binario = b;
	}

	// --- getters and setters --------------
	public String getCanzone() {
		return canzone;
	}
	public void setCanzone(String canzone) {
		this.canzone = canzone;
	}

	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}

	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getBinario() {
		return binario;
	}
	public void setBinario(String binario) {
		this.binario = binario;
	}
}
