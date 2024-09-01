package beans;

public class FinderThread extends Thread {
	
	private Libro[] libri;
	private Catalogo result;
	private String autore;
	private int start;

	public FinderThread(Libro[] libri, int start, Catalogo result, String autore) {
		this.libri = libri;
		this.result = result;
		this.autore = autore;
		this.start = start;
	}
	
	@Override
	public void run() {
		for (int i=start; i<start+10 && i<libri.length; i++) {
			if (libri[i].getAutore().equals(autore)) {
				result.aggiungiLibro(libri[i]);
			}
		}
	}

}
