package es_0_estensione;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Produttore {
	
	public static void main(String[] args) { 
		int i, controllo;
		InputStreamReader isr = new InputStreamReader(System.in);
		FileWriter fOut = null;
		char input;
		
		if(args.length==0) { 
			System.out.println("Inserire almeno il nome di un file");
			System.exit(1);
		}
		
		for(i=0; i<args.length; i++) {
			try {
				fOut = new FileWriter(args[i], true);
				System.out.print((i+1)+": ");
				
				while((controllo=isr.read())>=0) {
					input = (char) controllo;
					fOut.write(input);
					
					if(input=='\n') {
						System.out.print((i+1)+": ");
					}
				}
				System.out.println();
				fOut.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		try {
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Produttore terminato.");
	}
}