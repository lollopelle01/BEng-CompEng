

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Consumatore extends Thread{
	private String path;
	
	public Consumatore(String path) {
		this.path=path;
	}
	
	public void run() {
		try {
			BufferedReader br=new BufferedReader(new FileReader(path));
			String linea;
			while((linea=br.readLine())!=null) {
				System.out.println(linea);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
