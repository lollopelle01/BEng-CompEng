package es_0_estensione;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConsumatoreF extends Thread{
	private File file_vecchio, file_nuovo = new File(this.getName());
	private char[] prefix;
	private int controllo, i, length;
	private char input;
	private boolean flag;
	private FileReader fr;
	private FileWriter ftemp;
	
	public ConsumatoreF(File file, char[] prefix, int length) {
		super();
		this.file_vecchio=file;
		this.prefix=prefix;
		this.length=length;
	}
	
	@Override
	public void run() {
		try {
			fr = new FileReader(file_vecchio);
			
			ftemp= new FileWriter(file_nuovo);
			
			while((controllo=fr.read())>=0) {
				input = (char) controllo;
				flag=true;
				
				for(i=0; i<length && flag; i++) {
					if(prefix[i] == input) {
						flag=false;
					}
				}
				
				if(flag) {
					ftemp.write(input);
				}
				
			}
			
			fr.close();
			ftemp.close();
			
			file_vecchio.delete();
			file_nuovo.renameTo(file_vecchio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
