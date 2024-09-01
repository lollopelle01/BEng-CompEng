package it.unibo.paw.SAX;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Template_SAX_ContentHandler extends DefaultHandler{
	
	public Template_SAX_ContentHandler() {
		super();
	}
	
	private Set<String> hotels = new HashSet<String>();
	
	public Set<String> getSelezione(){
		return this.hotels;
	}
	
	// flag per muoverci tra i tag
	private boolean 	inNome=false, inCliccato=false;
	
	// contenuti della lettera
	private String 		nome, cliccato;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("nome_hotel")) 		{inNome=true;}
		if(rawName.equals("cliccato")) 				{inCliccato=true;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		int i=0; // numero di lettere

		if(inNome) {nome =new String(ch, start, length) ;}
		if(inCliccato) {cliccato =new String(ch, start, length) ;}
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("nome_hotel")) 		{inNome=false;}
		if(qName.equals("cliccato")) 			{
			inCliccato=false;
			if(cliccato.equals("true")) {hotels.add(nome);}
		}
	}

}


