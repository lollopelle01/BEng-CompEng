package it.unibo.paw.SAX;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private boolean 	inAlbum=false, inTipo=false, inAutore=false, 
						inNome=false, inTitolo=false, inDataInizio=false, 
						inDataFine=false, inFotografia=false, inDataScatto=false, inNomeFile=false,
						inTag=false, inPosizione=false, inNomeLuogo=false, inLatitudine=false, inLongitudine=false;
	
	// contenuti della lettera
	private String 		tipo, nome_luogo, autore, titolo;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("album")) 			{inAlbum=true;}
		if(rawName.equals("tipo")) 				{inTipo=true;}
		if(rawName.equals("autore")) 			{inAutore=true;}
		if(rawName.equals("nome")) 				{inNome=true;}
		if(rawName.equals("titolo")) 			{inTitolo=true;}
		if(rawName.equals("data_inizio"))		{inDataInizio=true;}
		if(rawName.equals("data_fine")) 		{inDataFine=true;}
		if(rawName.equals("fotografia")) 		{inFotografia=true;}
		if(rawName.equals("data_scatto")) 		{inDataScatto=true;}
		if(rawName.equals("nome_file")) 		{inNomeFile=true;}
		if(rawName.equals("tag")) 				{inTag=true;}
		if(rawName.equals("posizione")) 		{inPosizione=true;}
		if(rawName.equals("nome_luogo")) 		{inNomeLuogo=true;}
		if(rawName.equals("latitudine")) 		{inLatitudine=true;}
		if(rawName.equals("longitudine")) 		{inLongitudine=true;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		int i=0; // numero di lettere
		
		// Lettura dati
		if(inTipo)		{tipo=new String(ch, start, length);}
		if(inAutore)	{autore=new String(ch, start, length);}
		if(inTitolo)	{titolo=new String(ch, start, length);}
		if(inNomeLuogo)	{nome_luogo=new String(ch, start, length);}
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("album")) 			{inAlbum=false;}
		if(qName.equals("tipo")) 			{inTipo=false;}
		if(qName.equals("autore")) 			{inAutore=false;}
		if(qName.equals("nome")) 			{inNome=false;}
		if(qName.equals("titolo")) 			{inTitolo=false;}
		if(qName.equals("data_inizio"))		{inDataInizio=false;}
		if(qName.equals("data_fine")) 		{inDataFine=false;}
		if(qName.equals("fotografia")) 		{
			inFotografia=false;
			if(tipo.equals("DB") && nome_luogo.equals("Bologna")) {
				result.add(autore + ", " + titolo);
			}
		}
		if(qName.equals("data_scatto")) 	{inDataScatto=false;}
		if(qName.equals("nome_file")) 		{inNomeFile=false;}
		if(qName.equals("tag")) 			{inTag=false;}
		if(qName.equals("posizione")) 		{inPosizione=false;}
		if(qName.equals("nome_luogo")) 		{inNomeLuogo=false;}
		if(qName.equals("latitudine")) 		{inLatitudine=false;}
		if(qName.equals("longitudine")) 	{inLongitudine=false;}
	}

	// Eventuali metodi per visualizzare risultati in main
	public Set<String> getFotoBookBologna() throws IOException{
		return result;
	}

}


