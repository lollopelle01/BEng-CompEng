package edicecream.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.TreeMap;


public class MyIceCreamRepository implements IceCreamRepository{
	private TreeMap<String, Integer> map;
	
	private TreeMap<String, Integer> leggi(BufferedReader reader) throws BadFileFormatException, IOException{
		var result=new TreeMap<String, Integer>();
		try {
			String line;
			while((line=reader.readLine())!=null) {
				String[] gustoEpeso=line.split("-");
				String gusto=gustoEpeso[0].trim();
				Integer peso=Integer.valueOf(gustoEpeso[1].trim());
				if(gusto==null || gusto.isBlank() || gusto.isEmpty() || peso<0) {
					throw new BadFileFormatException();
				}
				else {
					result.put(gusto, peso);
				}
			}
		}
		catch(Exception e) {
			throw new BadFileFormatException();
		}
		return result;
		
	}
	
	public MyIceCreamRepository(Reader baseReader) throws BadFileFormatException, IOException {
		if(baseReader==null) {
			throw new IllegalArgumentException("Reader nullo");
		}
		try {
			this.map=this.leggi(new BufferedReader(baseReader));
		}
		catch(Exception e) {
			throw new BadFileFormatException();
		}
	}

	@Override
	public Set<String> getFlavors() {
		return this.map.keySet();
	}

	@Override
	public boolean removeQuantity(String flavor, int qty) {
		if(this.map.containsKey(flavor) && this.map.get(flavor)>=qty) {
			this.map.put(flavor, this.map.get(flavor)-qty);
			return true;
		}
		else
			return false;
	}

	@Override
	public Integer getAvailableQuantity(String flavor) {
		return this.map.get(flavor);
	}
}
