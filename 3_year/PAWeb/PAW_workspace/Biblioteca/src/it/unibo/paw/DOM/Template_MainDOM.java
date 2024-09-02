package it.unibo.paw.DOM;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import it.unibo.paw.Template_ErrorHandler;

public class Template_MainDOM {
	
	public static Set<String> getCognomi(String genere, int totAlbum){
		Set<String> result = new HashSet<String>();
		 try {
	         // Definiamo .xml da parsare
	         File inputFile = new File("src/it/unibo/paw/sources/biblioteca.xml");

	         // 1) Costruire un DocumentBuilder che validi il documento XML
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         dbFactory.setValidating(true);
	         dbFactory.setNamespaceAware(true);

	         // seguente istruzione per specificare che stiamo validando tramite XML Schema 
				dbFactory.setFeature("http://apache.org/xml/features/validation/schema",true);

	         // seguente istruzione per specificare che gli "ignorable whitespace" (tab, new line...) 
			   // tra un tag ed un altro devono essere scartati e non considerati come text node
				dbFactory.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
	         
	         // 2) Attivare un gestore di non-conformita'
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         dBuilder.setErrorHandler(new Template_ErrorHandler());				
	         
	         // 3) Parsificare il documento, ottenendo un documento DOM
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();

	         // 4) utilizzare il documento DOM
	         System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

	         // Estraiamo l'oggetto di interesse come lista di nodi
	         NodeList nMusicistaList = doc.getElementsByTagName("musicista");

	         // Esploriamo la lista di nodi per analizzare oggetto
	         for (int temp = 0; temp < nMusicistaList.getLength(); temp++) {
	            Node nMusicistaNode = nMusicistaList.item(temp);

	            if (nMusicistaNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eMusicistaElement = (Element) nMusicistaNode;
	               
	               // Debug
	               for(int i=0; i<eMusicistaElement.getChildNodes().getLength(); i++) {
	            	   System.out.println(eMusicistaElement.getChildNodes().item(i).getTextContent());
	               }
	               
	               if(eMusicistaElement.getChildNodes().item(2).getTextContent().equals(genere)
	            		   &&
	            	  eMusicistaElement.getChildNodes().getLength()-3>=totAlbum) {
	            	   result.add(eMusicistaElement.getChildNodes().item(1).getTextContent());
	               }
	              
	            }
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		 return result;
	}
	
	// da fa
	public static void main(String[] args){
		Set<String> cognomi = getCognomi("Rock", 3);
		try {
			FileWriter fw = new FileWriter("Biblioteca.txt");
			fw.write("I musicisti Rock con almeno 3 ALbum sono:\n");
			for(String c : cognomi) {
				fw.write(c + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
     
   }
	
	
}