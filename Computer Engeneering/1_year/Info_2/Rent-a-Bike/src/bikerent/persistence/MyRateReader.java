package bikerent.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TreeMap;

import bikerent.model.Periodo; 
import bikerent.model.Rate;

public class MyRateReader implements RateReader{
	private Periodo estraiPrimoPeriodo(String s) throws BadFileFormatException {
		String[] splittati=s.trim().split(" ");
		if(splittati.length!=5 || !splittati[1].equalsIgnoreCase("cent") || !splittati[2].equalsIgnoreCase("per") || !splittati[4].equalsIgnoreCase("minuti")) {
			throw new BadFileFormatException("Formato primo periodo");
		}
		int prezzo=Integer.parseInt(splittati[0]);
		int durata=Integer.parseInt(splittati[3]);
		return new Periodo(prezzo, Duration.ofMinutes(durata));
	}
	
	private Periodo estraiSuccessiviPeriodi(String s) throws BadFileFormatException {
		String[] splittati=s.trim().split(" ");
		if(splittati.length!=6 || !splittati[0].equalsIgnoreCase("poi") || !splittati[3].equalsIgnoreCase("per") || !splittati[5].equalsIgnoreCase("minuti") || !splittati[2].equalsIgnoreCase("cent")) {
			throw new BadFileFormatException("Formato altri periodi");
		}
		int prezzo=Integer.parseInt(splittati[1]);
		int durata=Integer.parseInt(splittati[4]);
		return new Periodo(prezzo, Duration.ofMinutes(durata));
	}
	
	private Optional<Duration> estraiDurataMassima(String s) throws BadFileFormatException{
		String[] splittati=s.trim().split(" ");
		if(splittati.length!=3 || !splittati[0].equalsIgnoreCase("max")) {
			throw new BadFileFormatException("Formato Durata massima");
		}
		if(!splittati[2].equalsIgnoreCase("ore"))
			return Optional.empty();
		else {
			return Optional.of(Duration.ofHours(Integer.parseInt(splittati[1])));
		}
	}
	
	private Optional<LocalTime> estraiOrarioMassimo(String s) throws BadFileFormatException{
		String[] splittati=s.trim().split(" ");
		if(splittati.length!=3 || !splittati[0].equalsIgnoreCase("max")) {
			throw new BadFileFormatException("Formato Durata massima");
		}
		if(!splittati[1].equalsIgnoreCase("entro"))
			return Optional.empty();
		else {
			return Optional.of(LocalTime.parse(splittati[2]));
		}
	}
	
	private double estraiSanzione(String s) throws BadFileFormatException {
		String[] splittati=s.trim().split(" ");
		if(splittati.length!=3 || !splittati[0].equalsIgnoreCase("sanzione") || !splittati[2].equalsIgnoreCase("euro")) {
			throw new BadFileFormatException("Formato Durata massima");
		}
		return Double.parseDouble(splittati[1]);
	}
	
	
	
	@Override
	public Map<String, Rate> readRates(Reader reader) throws IOException, BadFileFormatException {
		Map<String, Rate> result=new TreeMap<>();
		BufferedReader br=new BufferedReader(reader);
		String line;
		try {
			while((line=br.readLine())!=null) {
				String[] splittati=line.trim().split(",");
				if(splittati.length!=5) {
					throw new BadFileFormatException("Formato stringa");
				}
				String nome=splittati[0].trim();
				
				Periodo primoPeriodo=this.estraiPrimoPeriodo(splittati[1]);
				
				Periodo altriPeriodi=this.estraiSuccessiviPeriodi(splittati[2]);
				
				Optional<Duration> durata=this.estraiDurataMassima(splittati[3]);
				
				Optional<LocalTime> orario=this.estraiOrarioMassimo(splittati[3]);
				
				double tassa=this.estraiSanzione(splittati[4]);
				
				result.put(nome, new Rate(nome, primoPeriodo, altriPeriodi, durata, orario, tassa));
			}
			return result;
		}
		catch(Exception e) {
			throw new BadFileFormatException(e);
		}
	}

}
