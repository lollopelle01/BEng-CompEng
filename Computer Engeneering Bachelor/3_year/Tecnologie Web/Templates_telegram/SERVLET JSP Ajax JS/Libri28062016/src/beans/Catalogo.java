package beans;

import java.util.HashSet;
import java.util.Set;

public class Catalogo {
	
	private Set<Libro> libri = new HashSet<>();
	
	public Set<Libro> getLibri() {
		return libri;
	}
	
	public synchronized boolean aggiungiLibro(Libro libro) {
		return libri.add(libro);
	}

}
