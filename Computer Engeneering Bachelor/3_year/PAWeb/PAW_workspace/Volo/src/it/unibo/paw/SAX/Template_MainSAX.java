package it.unibo.paw.SAX;

import java.io.FileWriter;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.XMLReader;

import it.unibo.paw.Template_ErrorHandler;

public class Template_MainSAX {
	
	// uniamo tutti i componenti
	public static void main(String args[]) {

		try
		{
			// 1) Costruire un parser SAX che validi il documento XML
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setValidating(true);
			spf.setNamespaceAware(true);
			SAXParser saxParser = spf.newSAXParser();

			// 2) agganciare opportuni listener al lettore XML
			XMLReader xmlReader = saxParser.getXMLReader();
			// 2.1) ContentHandler
			Template_SAX_ContentHandler contentHandler = new Template_SAX_ContentHandler(); 	// da sostituire
			xmlReader.setContentHandler(contentHandler);
			// 2.2) ContentHandler
			ErrorHandler errorHandler = new Template_ErrorHandler();		// da sostituire
			xmlReader.setErrorHandler(errorHandler);

			// seguente istruzione per specificare che stiamo validando tramite XML Schema 
			xmlReader.setFeature("http://apache.org/xml/features/validation/schema",true);

			// 3) Parsificare il documento
			xmlReader.parse("src/it/unibo/paw/sources/volo.xml");
			
			// 4) Visualizzare risultato
			Set<String> result = contentHandler.voli();
			FileWriter fw = new FileWriter("src/it/unibo/paw/sources/Volo.txt");
			
			fw.write("Risultati ottenuti:\n");
			for(String res : result) {
				fw.write(res + "\n");
			}
			fw.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			System.exit(1);
		};

	}
}
