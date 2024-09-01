package media.filters;

import media.Media;

public class GenreFilter implements Filter{
	private String genere;

	public void setGenre(String genere) {
		this.genere = genere;
	}
	
	public GenreFilter(String genere) {
		setGenre(genere);
	}
	
	@SuppressWarnings("preview")
	public boolean filter(Media m) {
		if(m instanceof HasGenre h) {
			return (this.genere.equals(" ") || this.genere.equals(h.getGenre()));
		}
		return false;
	}
	
}
