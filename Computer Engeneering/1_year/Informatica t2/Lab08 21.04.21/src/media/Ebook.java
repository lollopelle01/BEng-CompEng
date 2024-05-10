package media;

import media.filters.HasGenre;
import utils.StringUtils;

public class Ebook extends Media implements HasGenre{
	private String[] authors;
	private String genre;
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	
	public Ebook(String titolo, int anno, String[] authors, String genre) {
		super(anno, titolo);
		this.authors=authors;
		this.genre=genre;
	}
	
	@Override
	public Type getType() {
		return Type.EBOOK;
	}
	
	@SuppressWarnings("preview")
	@Override
	public boolean equals(Object o) {
		if(o instanceof Ebook e) {
			return super.equals(o) && (StringUtils.areEquivalent(this.authors, e.authors) && this.genre==e.genre);
		}
		else
			return false;
	}
	
	@Override
	public String toString() {
		return super.toString()+", genere:"+this.genre+", autori:"+this.authors;
	}
}
