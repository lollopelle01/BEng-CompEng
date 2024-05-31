package it.unibo.paw.SAX;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Template_SAX_ContentHandler extends DefaultHandler{
	
	public Template_SAX_ContentHandler() {
		super();
	}
	
	private Set<String> video = new HashSet<String>();
	
	public Set<String> getVideo(){
		return video;
	}
	
	// flag per muoverci tra i tag
	private boolean 	inInterrogazione=false, inId=false, inKeyword=false, inTipologia=false, 
									inCardinalita=false, inRisultato=false, inTag=false;
	
	// contenuti della lettera
	private String 		tipo=null, idRichiesta=null, idRisultato=null, keyword=null, tag=null;
	private int				cardinalita, numVideo;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("interrogazione")) 		{inInterrogazione=true;}
		if(rawName.equals("id")) 							{inId=true;}
		if(rawName.equals("tag")) 						{inTag=true;}
		if(rawName.equals("keyword")) 				{inKeyword=true;}
		if(rawName.equals("tipologia")) 				{inTipologia=true;}
		if(rawName.equals("cardinalita")) 				{inCardinalita=true;}
		if(rawName.equals("risultato")) 					{inRisultato=true; numVideo=0;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		if(inId && !inRisultato) {idRichiesta = new String(ch, start, length);}
		if(inId && inRisultato) {idRisultato = new String(ch, start, length);}
		if(inTag) {tag = new String(ch, start, length);}
		if(inTipologia) {tipo = new String(ch, start, length);}
		if(inCardinalita) {cardinalita = Integer.parseInt(new String(ch, start, length)); System.out.println("cardinalita=" + cardinalita);}
		if(inKeyword) {keyword = new String(ch, start, length);}
	
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("interrogazione")) 		{inInterrogazione=false;}
		if(qName.equals("id")) 							{inId=false;}
		if(qName.equals("tag")) 						{inTag=false;}
		if(qName.equals("keyword")) 				{inKeyword=false;}
		if(qName.equals("tipologia")) 				{inTipologia=false;}
		if(qName.equals("cardinalita")) 				{inCardinalita=false;}
		if(qName.equals("risultato")) 				{inRisultato=false;}
		if(qName.equals("video") && inRisultato) {
			if(idRichiesta.equals( "ID 0542") && tipo.equals("per contenuto + semantica") 
					&& (keyword==null|keyword.equals("tramonto")) 
					&& numVideo <= cardinalita-1) {
				this.video.add(idRisultato + " , " + tag);
				numVideo++;
				idRisultato=null; tag=null;
			}
		}
	}

}


