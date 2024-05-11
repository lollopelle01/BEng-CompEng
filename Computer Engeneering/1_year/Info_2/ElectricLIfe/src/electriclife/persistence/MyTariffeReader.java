package electriclife.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.StringTokenizer;

import electriclife.model.Tariffa;
import electriclife.model.TariffaAConsumo;
import electriclife.model.TariffaFlat;

public class MyTariffeReader implements TariffeReader
{
	private Reader innerReader;
	private boolean aConsumoGiàTrovata;
	private NumberFormat formatter=NumberFormat.getCurrencyInstance(Locale.ITALY);
	
	public MyTariffeReader(Reader reader) {
		this.innerReader=reader;
		this.aConsumoGiàTrovata=false;
	}

	@Override
	public Collection<Tariffa> caricaTariffe() throws IOException, BadFileFormatException{
		Collection<Tariffa> result=new ArrayList<>();
		BufferedReader br=new BufferedReader(this.innerReader);
		String line;
		try {
			while((line=br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line, ";");
				String tipoTariffa=st.nextToken().trim();
				if(tipoTariffa.equals("FLAT")) {
					result.add(this.readFlat(st));
				}
				else if(tipoTariffa.equals("A CONSUMO")) {
					result.add(this.readConsumo(st));
					this.aConsumoGiàTrovata=true;
				}
				else
					throw new BadFileFormatException("tipo tariffa");
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}
	
	private Tariffa readConsumo(StringTokenizer st) throws BadFileFormatException{
		if(this.aConsumoGiàTrovata) throw new BadFileFormatException("A CONSUMO gia trovata");
		String value=st.nextToken("\n").trim();
		double valore;
		try {
			 valore=this.formatter.parse(value.substring(2, value.length())).doubleValue();
		}
		catch(Exception e) {
			throw new BadFileFormatException("Parse");
		}
		
		return new TariffaAConsumo("A CONSUMO", valore);
	}

	private Tariffa readFlat(StringTokenizer st) throws BadFileFormatException{
		String nome=st.nextToken().trim();
		if(nome.isEmpty()) throw new BadFileFormatException("nome");
		
		String[] splittati=st.nextToken(";").trim().split(" ");
		if(splittati.length!=2) throw new BadFileFormatException("campi soglia mancanti");
		if(!splittati[0].equals("SOGLIA")) throw new BadFileFormatException("SOGLIA");
		int soglia=Integer.parseInt(splittati[1]);
		double prezzoMensile;
		try {
			prezzoMensile=this.formatter.parse(st.nextToken().trim()).doubleValue();
		}
		catch(Exception e) {
			throw new BadFileFormatException("Parse");
		}
		
		
		@SuppressWarnings("unused")
		String virgola=st.nextToken(" "); //escludo ;
		
		String[] splittati2=st.nextToken("\n").trim().split(" ");
		if(splittati2.length!=4) throw new BadFileFormatException("campi extra mancanti");
		if(!splittati2[0].equals("KWh") || !splittati2[1].equals("EXTRA")) throw new BadFileFormatException("campi extra errati");
		String price=splittati2[2]+" "+splittati2[3];
		double prezzoExtra;
		try {
			prezzoExtra=this.formatter.parse(price).doubleValue();
		}
		catch(Exception e) {
			throw new BadFileFormatException("Parse");
		}
	
		return new TariffaFlat(nome, prezzoMensile, soglia, prezzoExtra);
	}

}