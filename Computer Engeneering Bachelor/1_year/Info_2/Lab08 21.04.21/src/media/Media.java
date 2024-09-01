package media;

import media.filters.HasType;

public abstract class Media implements HasType{
	private String title;
	private int year=-1;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Media(int year, String title) {
		this.year=year;
		this.title=title;
	}
		
	public abstract Type getType();
	
	@SuppressWarnings("preview")
	@Override
	public boolean equals(Object o) {
		if(o instanceof Media m)
			return this.title.equals(m.getTitle()) && this.year==m.getYear();
		else
			return false;	
	}
	
	@Override
	public String toString() {
		return "Tipo: " + this.getType() + ", titolo:" + this.getTitle() + ", anno:" + this.getYear();
	}


}
