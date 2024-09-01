package it.esame;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
				PrintWriter writer = new PrintWriter("Hotel.txt");
				writer.println("Selezionati i seguenti Alberghi dall'utente "+getAttributeByName(getNodeByName(domDocument.getDocumentElement(), "utente"), "email")+":");
				for (String albergo : getSelezione(domDocument)) {
					writer.println(albergo);
				}
				writer.close();
				
			    
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public static List<String> getSelezione(Document domDocument) {
		List<String> result = new ArrayList<>();
		for (Node risultato : getNodesByName(getNodeByName(domDocument.getDocumentElement(), "ricerca"), "risultato")) {
			String visitato = getAttributeByName(getNodeByName(risultato, "link"), "visitato");
			if (visitato.equals("si"))
				result.add(getNodeTextByNodeName(risultato, "nomehotel"));
		}
		return result;
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
