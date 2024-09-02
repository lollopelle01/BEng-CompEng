package it.unibo.paw.SAX;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Template_SAX_ContentHandler extends DefaultHandler{
	
	public Template_SAX_ContentHandler() {
		super();
	}
	
	private Set<String> result = new HashSet<String>();
	
	// flag per muoverci tra i tag
	private boolean 	inPrenotazione=false, inCodice=false, inCompagnia=false, 
						inTipoVolo=false;
	
	// contenuti della lettera
	private String 		codice=null, compagnia=null, tipoVolo;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("prenotazione")) 	{inPrenotazione=true;}
		if(rawName.equals("codice")) 		{inCodice=true;}
		if(rawName.equals("compagnia")) 	{inCompagnia=true;}
		if(rawName.equals("tipo_volo")) 	{inTipoVolo=true;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		int i=0; // numero di lettere

		if(inCodice) {codice=new String(ch, start, length);}
		if(inCompagnia) {compagnia=new String(ch, start, length);}
		if(inTipoVolo) {tipoVolo=new String(ch, start, length);}
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("prenotazione")){
			inPrenotazione=false;
			if(compagnia.equals("Alitalia") && tipoVolo.equals("Solo-Andata") && !result.contains(codice)) {
				result.add(codice);
			}
		}
		if(qName.equals("codice")) 		{inCodice=false;}
		if(qName.equals("compagnia")) 	{inCompagnia=false;}
		if(qName.equals("tipo_volo")) 	{inTipoVolo=false;}
	}

	// Eventuali metodi per visualizzare risultati in main
	Set<String> voli(){
		return result;
	}

}


