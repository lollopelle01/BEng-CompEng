package media;

import java.util.Arrays;

import utils.StringUtils;

public class Photo extends Media{
	private String[] authors=null;

	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	
	public Type getType() {
		return Type.PHOTO;
	}

	public Photo(String titolo, int anno, String[] authors) {
		super(anno, titolo);
		this.authors=authors;
	}
	@SuppressWarnings("preview")
	@Override
	public boolean equals(Object o) {
		if(o instanceof Photo p) {
			return super.equals(o) && StringUtils.areEquivalent(this.authors, p.authors);
		}
		else
			return false;
	}
	
	@Override
	public String toString() {
		return "Photo authors=" + Arrays.toString(authors);
	}
}
