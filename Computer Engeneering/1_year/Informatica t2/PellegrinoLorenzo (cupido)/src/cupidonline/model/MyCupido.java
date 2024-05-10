package cupidonline.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class MyCupido extends Cupido{

	public MyCupido(Map<String, Persona> iscritti, Map<String, Preferenza> preferenze) {
		super(iscritti, preferenze);
	}

	@Override
	public SortedSet<Corrispondenza> trovaCorrispondenze(String pName, Preferenza pPref) {
		SortedSet<Corrispondenza> result=new TreeSet<Corrispondenza>();
		
		for(Persona p: super.getIscritti()) {
			if(super.verificaSesso(p, pPref)) {
				result.add(super.calcolaCorrispondenza(p, pPref, pName));
			}
		}
		
		return result;
	}

	@Override
	public int indiceCompatibilità(Persona q, Preferenza pref) {
		int etaR=100;
		int luogoR=100;
		int segnoR=100;
		int altezzaR=100;
		int pesoR=100;
		int occhiR=100;
		int capelliR=100;
		
		if(!pref.etaInRange(q.getEta())) { 
			etaR=etaR-5*pref.etaOutOfRange(q.getEta());
		}
		
		if(!pref.getCitta().equalsIgnoreCase(q.getCitta())) {
			if(!pref.getProvincia().equalsIgnoreCase(q.getProvincia())) {
				if(!pref.getRegione().equalsIgnoreCase(q.getRegione())) {
					luogoR=40;
				}
				else
					luogoR=60;
			}
			else
				luogoR=90;
		}
		
		if(pref.getSegnoZodiacale().isPresent()) {
			if(!pref.getSegnoZodiacale().get().equals(q.getSegnoZodiacale()))
				segnoR=90;
		}
		
		if(pref.getAltezza().isPresent()) {
			if(!pref.getAltezza().get().equals(q.getAltezza()))
				altezzaR=Math.round((altezzaR-100*Math.abs(pref.getAltezza().get()-q.getAltezza())));
		}
		
		if(pref.getPeso().isPresent()) {
			if(pref.getPeso().getAsInt()!=q.getPeso())
				pesoR= Math.round(pesoR-Math.abs(pref.getPeso().getAsInt() -q.getPeso()));
		}
		
		if(pref.getColoreOcchi().isPresent()) {
			if(!pref.getColoreOcchi().get().equals(q.getColoreOcchi()))
				occhiR=95;
		}
		
		if(pref.getColoreCapelli().isPresent()) {
			if(!pref.getColoreCapelli().get().equals(q.getColoreCapelli()))
				capelliR=95;
		}
		
		int[] risultati= {etaR, luogoR, segnoR, altezzaR, pesoR, occhiR, capelliR};
		Arrays.sort(risultati);
		System.out.println(risultati);
		
		return risultati[0];
		
	}

}
