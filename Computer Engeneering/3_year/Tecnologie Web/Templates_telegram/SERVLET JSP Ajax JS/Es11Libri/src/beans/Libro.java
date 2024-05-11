package beans;

public class Libro {
	
	private String nome = "";
	private String autore = "";
	private String ISBN = "";
	
	public Libro() {
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if (nome != null)
			this.nome = nome;
		else
			this.nome = "";
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		if (autore != null)
			this.autore = autore;
		else
			this.autore = "";
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		if (ISBN != null)
			this.ISBN = ISBN;
		else
			this.ISBN = "";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ISBN.hashCode();
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (!ISBN.equals(other.getISBN()))
			return false;
		return true;
	}
	

}
