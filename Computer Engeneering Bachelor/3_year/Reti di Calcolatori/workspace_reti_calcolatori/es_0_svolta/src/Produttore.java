

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Produttore extends Thread{	
	private String path;
	
	public Produttore(String fileName) {
		this.path="/Users/pelle/Desktop/"+fileName+".txt";
	}
	
	public void run() {
		try {
			FileWriter fw=new FileWriter(new File(path));
		
			Scanner lines = new Scanner(System.in);
			
			System.out.println("Quante righe vuoi scivere?");
			int numRighe=Integer.valueOf(lines.nextLine());
			
			System.out.println("Cosa vuoi scrivere?");
			for(int i=0; i<numRighe; i++) {
				fw.write(lines.nextLine()+"\n");
			}
			lines.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
}
