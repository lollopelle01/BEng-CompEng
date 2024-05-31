package it.unibo.paw.SAX;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Template_SAX_ContentHandler extends DefaultHandler{
	
	public Template_SAX_ContentHandler() {
		super();
	}
	
	// flag per muoverci tra i tag
	private boolean 	inLettera=false, inMittente=false, inData=false, 
						inDestinatario=false, inOggetto=false, inSaluto=false, 
						inCorpo=false, inParagrafo=false, inChiusura=false, inFirma=false;
	
	// contenuti della lettera
	private String 		Lettera=null, data=null;
	
	// start-tag
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts) {
		//System.out.println("Leggo il tag: " + rawName);
		
		if(rawName.equals("lettera")) 		{inLettera=true; Lettera="";}
		if(rawName.equals("mittente")) 		{inMittente=true;}
		if(rawName.equals("data")) 			{inData=true;}
		if(rawName.equals("destinatario")) 	{inDestinatario=true;}
		if(rawName.equals("oggetto")) 		{inOggetto=true;}
		if(rawName.equals("saluto-cortese")){inSaluto=true;}
		if(rawName.equals("corpo")) 		{inCorpo=true;}
		if(rawName.equals("paragrafo")) 	{inParagrafo=true;}
		if(rawName.equals("chiusura")) 		{inChiusura=true;}
		if(rawName.equals("firma")) 		{inFirma=true;}
	}
	
	//tag-content
	public void characters(char ch[], int start, int length) {
		int i=0; // numero di lettere

		if(inLettera) {Lettera += "Lettera n." + (++i) + "\n"; inLettera=false;}
		if(inMittente) {Lettera += "Da: " + new String(ch, start, length) + "\n";}
		if(inData) {data = "In data " + new String(ch, start, length) + "\n";}
		if(inDestinatario) {Lettera += "A: " + new String(ch, start, length) + "\n" + data + "\n";}
		if(inOggetto) {Lettera += "Oggetto: " + new String(ch, start, length) + "\n";}
		if(inSaluto) {Lettera += new String(ch, start, length) + ",\n";}
		if(inCorpo) {} // non serve
		if(inParagrafo) {Lettera += new String(ch, start, length) + "\n";} 
		if(inChiusura) {Lettera += new String(ch, start, length) + "\n";}
		if(inFirma) {Lettera += "\n" + new String(ch, start, length) + "\n";}
	}
	
	//end-tag
	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("Leggo il tag: /" + qName);
		
		if(qName.equals("lettera")) 		{System.out.println(Lettera);}
		if(qName.equals("mittente")) 		{inMittente=false;}
		if(qName.equals("data")) 			{inData=false;}
		if(qName.equals("destinatario")) 	{inDestinatario=false;}
		if(qName.equals("oggetto")) 		{inOggetto=false;}
		if(qName.equals("saluto-cortese")) 	{inSaluto=false;}
		if(qName.equals("corpo")) 			{inCorpo=false;}
		if(qName.equals("paragrafo")) 		{inParagrafo=false;}
		if(qName.equals("chiusura")) 		{inChiusura=false;}
		if(qName.equals("firma")) 			{inFirma=false;}
	}

	// Eventuali metodi per visualizzare risultati in main
	// ...

}


