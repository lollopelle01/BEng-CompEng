package dentinia.governor.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import dentinia.governor.model.Elezioni;
import dentinia.governor.model.Partito;

public class MyVotiReader implements VotiReader{
	private Elezioni elezioni;
	private BufferedReader br;
	private long seggiDaAssegnare;
	
	@Override
	public Elezioni getElezioni() {
		return this.elezioni;
	}
	
	public MyVotiReader(Reader reader) throws BadFileFormatException {
		if(reader==null) {
			throw new IllegalArgumentException("reader");
		}
		this.br=new BufferedReader(reader);
		this.elezioni=this.caricaElementi();
	}
	
	private Elezioni caricaElementi() throws BadFileFormatException {
		String line;
		
		try {
			Elezioni result;
			
			line=this.br.readLine().trim();
			String[] splittati=line.split(" ");
			if(splittati.length!=2) throw new BadFileFormatException("campi mancanti");
			if(!splittati[0].equals("SEGGI")) throw new BadFileFormatException("campi errati");
			this.seggiDaAssegnare=Long.parseLong(splittati[1].trim());
			
			result=new Elezioni(this.seggiDaAssegnare);
			line=this.br.readLine().trim();
			String[] splittati1=line.split(",");
			for(int i=0; i<splittati1.length; i++) {
				StringTokenizer st=new StringTokenizer(splittati1[i], ".1234567890");	//perchè?
				
				String nome=st.nextToken().trim();
				if(nome.isEmpty()) throw new BadFileFormatException("campi errati");
				
				NumberFormat formatter=NumberFormat.getInstance(Locale.ITALY);
				long voti=formatter.parse(st.nextToken("\n").trim()).longValue();
				
				result.setVoti(new Partito(nome), voti);
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
	
	public static void main(String args[]) throws IOException, BadFileFormatException {
        VotiReader vReader = new MyVotiReader(new BufferedReader(new FileReader("Voti.txt")));
        System.out.println(vReader.getElezioni());
    }


}
