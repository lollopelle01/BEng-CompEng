package media;

import media.filters.HasDuration;
import media.filters.HasGenre;
import utils.StringUtils;

public class Film extends Media implements HasGenre, HasDuration{
	 private String[] actors;
	 private String director;
	 private int duration=-1;
	 private String genre;
	 
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String[] getActors() {
		return actors;
	}
	public void setActors(String[] actors) {
		this.actors = actors;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Film(String titolo, int anno, String regista, int duration, String[] attori, String genre) {
		super(anno, titolo);
		this.actors=attori;
		this.director=regista;
		this.duration=duration;
		this.genre=genre;
	}
	
	@SuppressWarnings("preview")
	@Override
	public boolean equals(Object o) {
		if(o instanceof Film f) {
			return super.equals(o) && (this.director==f.director && StringUtils.areEquivalent(this.actors, f.actors) && this.duration==f.duration && this.genre==f.genre);
		}
		else
			return false;
	}
	
	@Override
	public Type getType() {
		return Type.FILM;
	}
	
	@Override
	public String toString() {
		return super.toString() + ",è diretto da: " + this.director + ",dura:" + this.duration + ",ha come attori: " +this.actors + ", genere:" + this.genre;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
