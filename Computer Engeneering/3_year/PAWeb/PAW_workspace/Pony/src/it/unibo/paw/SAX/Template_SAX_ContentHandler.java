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
	private boolean 	inGiocattolo=false, inId=false, inCategoria=false, 
						inGT=false, inGD=false, inTitolo=false, inModalita=false, 
						inAutore=false, inCasa=false, inAnno=false, inFascia=false,
						inLingua=false, inDurata=false, inNome=false, inMarca=false;
	
	// contenuti della lettera
	private String 		nome=null, marca=null, fascia, modalita;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("giocattolo")) 		{inGiocattolo=true;}
		if(rawName.equals("id")) 				{inId=true;}
		if(rawName.equals("categoria")) 		{inCategoria=true;}
		if(rawName.equals("GT")) 				{inGT=true;}
		if(rawName.equals("GD")) 				{inGD=true;}
		if(rawName.equals("titolo"))			{inTitolo=true;}
		if(rawName.equals("autore")) 			{inAutore=true;}
		if(rawName.equals("casa_produttrice")) 	{inCasa=true;}
		if(rawName.equals("anno_produzione")) 	{inAnno=true;}
		if(rawName.equals("fascia_eta")) 		{inFascia=true;}
		if(rawName.equals("lingua")) 			{inLingua=true;}
		if(rawName.equals("durata")) 			{inDurata=true;}
		if(rawName.equals("nome")) 				{inNome=true;}
		if(rawName.equals("marca")) 			{inMarca=true;}
		if(rawName.equals("modalita")) 			{inModalita=true;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		//new String(ch, start, length)
		if(inNome)		{nome=new String(ch, start, length);}
		if(inMarca)		{marca=new String(ch, start, length);}
		if(inFascia)	{fascia=new String(ch, start, length);}
		if(inModalita)	{modalita=new String(ch, start, length);}
		
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("giocattolo")){
			inGiocattolo=false;
			if(fascia.equals("6-8") && modalita.equals("GI")) {
				result.add(nome + ", " + marca);
			}
		}
		if(qName.equals("id")) 					{inId=false;}
		if(qName.equals("categoria")) 			{inCategoria=false;}
		if(qName.equals("GT")) 					{inGT=false;}
		if(qName.equals("GD")) 					{inGD=false;}
		if(qName.equals("titolo"))				{inTitolo=false;}
		if(qName.equals("autore")) 				{inAutore=false;}
		if(qName.equals("casa_produttrice")) 	{inCasa=false;}
		if(qName.equals("anno_produzione")) 	{inAnno=false;}
		if(qName.equals("fascia_eta")) 			{inFascia=false;}
		if(qName.equals("lingua")) 				{inLingua=false;}
		if(qName.equals("durata")) 				{inDurata=false;}
		if(qName.equals("nome")) 				{inNome=false;}
		if(qName.equals("marca")) 				{inMarca=false;}
		if(qName.equals("modalita")) 			{inModalita=false;}
	}

	// Eventuali metodi per visualizzare risultati in main
	public Set<String> getGiocoTradizionale_Fascia6_8_GI(){
		return result;
	}

}


