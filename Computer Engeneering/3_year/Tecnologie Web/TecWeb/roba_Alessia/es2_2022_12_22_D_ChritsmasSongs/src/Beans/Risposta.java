package Beans;

import java.util.ArrayList;
import java.util.List;

public class Risposta {
	
	private List<Canzone> downloadedSongs = new ArrayList<Canzone>();
	private int numero;
	
	public Risposta() {
		super();
	}

	public Risposta(List<Canzone> canzoni, int num) {
		super();
		this.downloadedSongs = canzoni;
		this.numero = num;
	}

	public List<Canzone> getDownloadedSongs() {
		return downloadedSongs;
	}

	public void setDownloadedSongs(List<Canzone> downloadedSongs) {
		this.downloadedSongs = downloadedSongs;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}


}
