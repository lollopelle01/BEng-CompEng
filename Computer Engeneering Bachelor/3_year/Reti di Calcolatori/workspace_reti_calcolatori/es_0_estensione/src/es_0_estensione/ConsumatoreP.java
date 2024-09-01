package es_0_estensione;

import java.io.File;

public class ConsumatoreP {
	public static void main(String[] args) {
		int i;
		ConsumatoreF figli[];
		File check;
		char[] prefix = null;
	
		
		if(args.length<2) {
			System.out.println("Passare almeno un prefisso e un nome di file");
			System.exit(1);
		}
		figli = new ConsumatoreF[args.length-1];
		
		prefix = args[0].toCharArray();
		
		for(i=1; i<args.length; i++) {
			check = new File(args[i]);
			System.out.println(check.toString());
			if(!check.isFile()) { 
				//errore di gestione dei file nella directory locale, sembra non conti src e package e quindi non li trova
				System.out.println(check.getAbsolutePath());
				System.out.println(System.getProperty("user.dir"));
				System.out.println("ERRORE-> file inesistente: "+args[i]);
				System.exit(1);
			}
				
			figli[i-1]=new ConsumatoreF(check, prefix, args[0].length());
			figli[i-1].run();
		}
		
		System.out.println("Padre terminato");
	}
}