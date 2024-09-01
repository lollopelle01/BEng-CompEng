package beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ExecThread extends Thread{
	private String canzone;
	
	private int count;
	private String titolo;
	private long dimensione;
	private String formato;
	private String contenuto;
	
	public String getCanzone() {
		return canzone;
	}

	public void setCanzone(String canzone) {
		this.canzone = canzone;
	}
	
	@Override
	public void run() {
		System.out.println("Thread: avviato thread per  " + this.getCanzone());
		try {
			Thread.sleep(1000*30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//esecuzione thread
		File f = new File(this.getCanzone());
		this.setTitolo(f.getName());
		this.setDimensione(f.length());


		String fileType = "Undetermined";
		try{
			fileType = Files.probeContentType(f.toPath());
		} catch (IOException ioException){
			System.out.println("Thread: File type not detected for " + f.getName());
		}
		this.setFormato(fileType);
		String cont = "";
		try{
	            InputStream inputStream = new FileInputStream(f);
	 
	            int byteRead = -1;
	            
	            while ((byteRead = inputStream.read()) != -1) {
	                cont += byteRead;
	            }
	            
	            inputStream.close();
	 
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
		this.setContenuto(cont);
	}

	

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public long getDimensione() {
		return dimensione;
	}

	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
}
