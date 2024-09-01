package beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Canzone {

	public String nome;
	public int durata;
	public String formato;
	public List<Character> contenuto;
	
	public Canzone(File file) {
		nome = file.getName();
		durata = (new Random()).nextInt(300);
		formato = "mp3";
		contenuto = new ArrayList<>();
		
		FileReader r;
		
		try {
			r = new FileReader(file);

			int i;
			while ((i = r.read()) != -1) {
				contenuto.add((char) i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
