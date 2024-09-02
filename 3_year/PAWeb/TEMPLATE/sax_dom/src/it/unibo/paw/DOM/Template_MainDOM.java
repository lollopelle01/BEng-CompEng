package it.unibo.paw.DOM;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import it.unibo.paw.Template_ErrorHandler;

public class Template_MainDOM {
	
	// da fa
	public static void main(String[] args){

      try {
         // Definiamo .xml da parsare
         File inputFile = new File("src/sources/file.xml");

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
         NodeList nList = doc.getElementsByTagName("lettera");

         // Esploriamo la lista di nodi per analizzare oggetto
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               // inizio lettera
               System.out.println("Lettera n." + (temp+1));
               System.out.println("Da: " + eElement.getElementsByTagName("mittente").item(0).getTextContent());
               System.out.println("A: " + eElement.getElementsByTagName("destinatario").item(0).getTextContent());
               System.out.println("In data: " + eElement.getElementsByTagName("data").item(0).getTextContent());
               System.out.println("\nOggetto: " + eElement.getElementsByTagName("oggetto").item(0).getTextContent());
               System.out.println(eElement.getElementsByTagName("saluto-cortese").item(0).getTextContent() + ",");
               
               // corpo del messaggio
               Node corpo = eElement.getElementsByTagName("corpo").item(0);
               NodeList paragrafi = corpo.getChildNodes();
               for(int i=0; i<paragrafi.getLength(); i++) {
            	   if(paragrafi.item(i).getNodeType() == Node.ELEMENT_NODE) {
            		   System.out.println(paragrafi.item(i).getTextContent().trim());
            	   }
               }
               
               // fine del messaggio
               System.out.println(eElement.getElementsByTagName("chiusura").item(0).getTextContent().strip());
               System.out.println("\n" + eElement.getElementsByTagName("firma").item(0).getTextContent());
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}