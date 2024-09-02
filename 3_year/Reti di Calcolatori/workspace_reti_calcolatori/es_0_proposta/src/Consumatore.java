
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Consumatore {
	public Consumatore() {}
	public static void main(String args[]) {
		BufferedReader br = null;
		int trovato, carattere_int;
		char carattere;
		if(args.length!=1 && args.length!=2) {
			System.out.println("Errore, numero argomenti"+args.length+"\n");
			System.exit(1);
		}
		
		else if(args.length==1) { 	//stringa 
			br=new BufferedReader(new InputStreamReader(System.in));
		}
		
		else { //args.lenght==2		//stringa + file
			try {
				br=new BufferedReader(new FileReader(args[1]));
			} catch (FileNotFoundException e) {
				System.out.println("File non trovato: "+args[1]+"\n");
				e.printStackTrace();
			}
		}
	
		try {
			while((carattere_int=br.read())>0) {
				trovato=0;
				carattere= (char) carattere_int;
				for(int i=0; i<args[0].length(); i++) {
					if(args[0].charAt(i)==carattere)
						trovato=1;
				}
				if(trovato==0) System.out.print(carattere);
			}
			br.close();
			
		} catch (IOException e) {
			System.out.println("Errore di lettura\n");
			e.printStackTrace();
		}
	}
}
