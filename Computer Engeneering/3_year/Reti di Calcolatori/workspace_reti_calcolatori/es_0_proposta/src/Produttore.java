import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Produttore {
	public Produttore() {}
	
	public static void main(String args[]) {
		if(args.length!=1) {
			System.out.println("Errore numero argomenti: "+args.length+"\n");
		}
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(args[0]);
		} catch (IOException e) {
			System.out.println("Errore apertura/scrittura file "+args[0]+"\n");
			e.printStackTrace();
		}
		
		System.out.println("Cosa vuoi scrivere?\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String linea;
		try {
			while((linea=br.readLine()) != null) {
				fw.write(linea+"\n");
			}
			System.out.print("\nRaggiunto EOF\n");
			br.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Errore lettura\n");
			e.printStackTrace();
		}
	}
}
