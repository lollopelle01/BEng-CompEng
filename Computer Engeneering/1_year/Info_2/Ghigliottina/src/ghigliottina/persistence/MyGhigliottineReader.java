package ghigliottina.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ghigliottina.model.Esatta;
import ghigliottina.model.Ghigliottina;
import ghigliottina.model.Terna;

public class MyGhigliottineReader implements GhigliottineReader{
	private List<Ghigliottina> ghigliottine;

	@Override
	public List<Ghigliottina> getGhigliottine() {
		return this.ghigliottine;
	}

	@Override
	public List<Ghigliottina> readAll(BufferedReader reader) throws IOException, BadFileFormatException {
		List<Ghigliottina> result=new ArrayList<>();
		try {
			Ghigliottina tmp;
			while((tmp=this.parseOne(reader))!=null) {
				result.add(tmp);
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
	
	private Ghigliottina parseOne(BufferedReader br) throws IOException, BadFileFormatException {
		List<Terna> terne=new ArrayList<>();
		String rispostaEsatta=null;
		int i=0;
		String line2;
		while((line2=br.readLine())!=null &&  !line2.equals("------------------------")) {
			if(!line2.contains("=")) {
				throw new BadFileFormatException("formato errato");
			}
			else {
				String[] splittati=line2.trim().split("=");
				if(splittati.length!=2)
					throw new BadFileFormatException("formato errato");
					
				if(splittati[0].trim().contains("/")) {
					String[] splittati2=splittati[0].trim().split("/");
					if(splittati2.length!=2)
						throw new BadFileFormatException("formato errato");
					
					String parola1=splittati2[0].trim();
					if(parola1.isEmpty())
						throw new BadFileFormatException("manca la prima parola");
					
					String parola2=splittati2[1].trim();
					if(parola2.isEmpty())
						throw new BadFileFormatException("manca la seconda parola");
					
					if(splittati[1].trim().isEmpty())
						throw new BadFileFormatException("manca la terza parola");
					if(!splittati[1].trim().equals("FIRST") && !splittati[1].trim().equals("SECOND"))
						throw new BadFileFormatException("la terza parola è errata");
					Esatta qualeDelleDue=Esatta.valueOf(splittati[1].trim());
					
					terne.add(new Terna(parola1, parola2, qualeDelleDue));
				}
				else {
					if(i==1) {
						throw new BadFileFormatException("seconda risposta esatta");
					}
					String formato=splittati[0].trim();
					if(!formato.equals("Risposta esatta")) {
						throw new BadFileFormatException("formato errato");
					}
					
					rispostaEsatta=splittati[1].trim();
					if(rispostaEsatta.isEmpty()) {
						throw new BadFileFormatException("manca risposta esatta");
					}	
					i=1;
				}
			}
		}
		if(line2==null)
			return null;
		return new Ghigliottina(terne, rispostaEsatta);
	}
	
	public static void main(String[] args) throws FileNotFoundException, BadFileFormatException, IOException {
		GhigliottineReader rdr = new MyGhigliottineReader();
		rdr.readAll(new BufferedReader(new FileReader("Ghigliottine.txt")));
	}
	
	

}
