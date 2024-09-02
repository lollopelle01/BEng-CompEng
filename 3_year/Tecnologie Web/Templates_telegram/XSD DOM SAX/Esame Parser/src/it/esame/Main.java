package it.esame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;

public class Main {
	
	public static void main(String args[]) {
		String xmlFilename;
		if (args.length != 1) {
			System.out.println("usage: " + Main.class.getSimpleName() + " xmlFilename");
		} else {
			try {
				xmlFilename = args[0];
				// Useful resource: http://xerces.apache.org/xerces2-j/features.html
	
				String schemaFeature = "http://apache.org/xml/features/validation/schema";
				String ignorableWhitespace = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
				
				// SAX 
				// 1) Costruire un parser SAX che validi il documento XML
				SAXParserFactory spf = SAXParserFactory.newInstance();
				spf.setValidating(true);
				spf.setNamespaceAware(true);
				SAXParser saxParser = spf.newSAXParser();
				
				// 2) agganciare opportuni listener al lettore XML
				XMLReader xmlReader = saxParser.getXMLReader();
				ErrorHandler errorHandler = new ErrorHandler();
			    xmlReader.setErrorHandler(errorHandler);
				// (unico content handler per tutti i DTD/XSD; 
				// sarebbe pi� corretto content handler diversi per DTD/XSD diversi)
				SAXContentHandler handler = new SAXContentHandler();
			    xmlReader.setContentHandler(handler);
	//
	//			// seguente istruzione per specificare che stiamo validando tramite XML Schema 
				xmlReader.setFeature(schemaFeature,true);
				
	//			// 3) Parsificare il documento
				xmlReader.parse(xmlFilename);
			    
				// 4) visualizzare il risultato
				System.out.println("Stampo le scelte:");
				for (Scelta scelta : handler.getScelte()) {
					System.out.println("Scelta di categoria "+scelta.getTipo()+", capi selezionati:");
					for (Capo capo : scelta.getCapi()) {
						System.out.println("Capo: "+capo.getDescription()+", "+capo.getImage()+", "+capo.getPrice());
					}
				}
				
//				System.out.println("SAX IgnorableWhitespace = " + handler.getIgnorableWhitespace());
//				System.out.println("SAX PeopleAmount = " + handler.getPeopleAmount());
//				System.out.println("SAX PeoplePreMM = " + handler.getPeoplePreMM());
//				System.out.println("SAX DonTel = " + handler.getDonTel());
	//			
				
				// DOM
				// 1) Costruire un DocumentBuilder che validi il documento XML
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setValidating(true);
				dbf.setNamespaceAware(true);
				
				// seguente istruzione per specificare che stiamo validando tramite XML Schema 
				dbf.setFeature(schemaFeature,true);
				
				// seguente istruzione per specificare che gli "ignorable whitespace" (tab, new line...) 
				// tra un tag ed un altro devono essere scartati e non considerati come text node
				dbf.setFeature(ignorableWhitespace, false);
				
				// 2) Attivare un gestore di non-conformita'
				DocumentBuilder db = dbf.newDocumentBuilder();
				db.setErrorHandler(new ErrorHandler());
				
				// 3) Parsificare il documento, ottenendo un documento DOM
				Document domDocument = db.parse(new File(xmlFilename));
				domDocument.getDocumentElement().normalize();
				
				// 4) utilizzare il documento DOM
				System.out.println("Stampo le scelte:");
				for (Scelta scelta : DomFunctions.getScelte(domDocument)) {
					System.out.println("Scelta di categoria "+scelta.getTipo()+", capi selezionati:");
					for (Capo capo : scelta.getCapi()) {
						System.out.println("Capo: "+capo.getDescription()+", "+capo.getImage()+", "+capo.getPrice());
					}
				}
				
//				System.out.println("DOM PeopleAmount = " + getPeopleAmount(domDocument));
//				System.out.println("DOM PeoplePreMM = " + getPeoplePreMM(domDocument));
//				System.out.println("DOM DonTel1 = " + getTel("Don", domDocument) );
//				System.out.println("DOM MickeyTel1 = " + getTel("Mickey", domDocument) );
//				setTel("Donald", "Duck", "1234", domDocument);
//				setTel("Mickey", "Mouse", "5678", domDocument);
//				System.out.println("DOM DonTel2 = " + getTel("Don", domDocument) );
//				System.out.println("DOM MickeyTel2 = " + getTel("Mickey", domDocument) );
			    
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public static String getAttributeByName(Node node, String attrName){
		return node.getAttributes().getNamedItem(attrName).getTextContent();
	}
	
	public static Node getNodeByName(Node root, String name){
		NodeList childnodes = root.getChildNodes();
		for(int i=0; i<childnodes.getLength(); i++){
			Node ni = childnodes.item(i);
			if(ni.getNodeName().equals(name)){
				return ni;
			}
		}
		return null;
	}
	
	public static String getNodeTextByNodeName(Node root, String name){
		NodeList childnodes = root.getChildNodes();
		for(int i=0; i<childnodes.getLength(); i++){
			Node ni = childnodes.item(i);
			if(ni.getNodeName().equals(name)){
				return ni.getTextContent();
			}
		}
		return null;
	}
	
	
	//se il nodo e' un element oppure document, basta usare root.getElementsByTagName(name);
	//Che pero' ritorna una NodeList
	public static Set<Node> getNodesByName(Node root, String name){
		NodeList childnodes = root.getChildNodes();
		Set<Node> ret = new HashSet<Node>();
		for(int i=0; i<childnodes.getLength(); i++){
			Node ni = childnodes.item(i);
			if(ni.getNodeName().equals(name)){
				ret.add(ni);
			}
		}
		if(!ret.isEmpty() && ret.size() > 0)
			return ret;
		else return null;
	}

}
