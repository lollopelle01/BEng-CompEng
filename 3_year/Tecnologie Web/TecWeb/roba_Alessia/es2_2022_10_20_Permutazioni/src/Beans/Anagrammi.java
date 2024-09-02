package Beans;

import java.util.ArrayList;
import java.util.List;

public class Anagrammi {

	private List<String> anagrammi = new ArrayList<>();
	private long time;
	
	public Anagrammi(List<String> anagrammi, long time) {
		this.anagrammi = anagrammi;
		this.time = time;
	}
	
	
}
