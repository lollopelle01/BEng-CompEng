package beans;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Catalogo {
	
	private Set<Libro> libri = new HashSet<>();
	
	public Catalogo() {
		
	}
	
	public Set<Libro> getLibri() {
		return libri;
	}
	
	public boolean aggiungiLibro(Libro libro) {
		if (libro.getNome().equals("") || libro.getAutore().equals("") || libro.getISBN().equals(""))
			return false;
		return libri.add(libro);
	}
	
	public Set<Libro> getByAutore(String autore) {
		return libri.stream().filter(libro -> libro.getAutore().equals(autore)).collect(Collectors.toSet());
	}

}
