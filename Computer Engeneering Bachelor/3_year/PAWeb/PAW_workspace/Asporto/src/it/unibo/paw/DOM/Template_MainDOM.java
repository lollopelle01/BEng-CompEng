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
         File inputFile = new File("sources/asporto.xml");
         File outputFile = new File("Asporto.txt");
         FileWriter fw = new FileWriter(outputFile);

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
         NodeList nList = doc.getElementsByTagName("ristorante");

         // Esploriamo la lista di nodi per analizzare oggetto
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
               // Cicliamo su categorie
               int proposte_tot = 0;
               
               // 1) Antipasti
               Node antipasti = eElement.getElementsByTagName("antipasti").item(0);
               proposte_tot += antipasti.getChildNodes().getLength();
               
               // 2) Primi
               Node primi = eElement.getElementsByTagName("primi").item(0);
               proposte_tot += primi.getChildNodes().getLength();
               
               // 3) Secondi
               Node secondi = eElement.getElementsByTagName("secondi").item(0);
               proposte_tot += secondi.getChildNodes().getLength();
               
               // 4) Contorni
               Node contorni = eElement.getElementsByTagName("contorni").item(0);
               proposte_tot += contorni.getChildNodes().getLength();
               
               // 5) Dessert
               Node dessert = eElement.getElementsByTagName("dessert").item(0);
               proposte_tot += dessert.getChildNodes().getLength();
               
               String risposta = eElement.getElementsByTagName("nome").item(0).getTextContent() + ": " + proposte_tot + " portate offerte\n";
               
               // Stampa
               System.out.print(risposta);
               fw.write(risposta);
            }
         }
         fw.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}