package it.unibo.paw.DOM;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import it.unibo.paw.Template_ErrorHandler;

public class Template_MainDOM {
	
	// da fa
	public static void main(String[] args){

      try {
         // Definiamo .xml da parsare
         File inputFile = new File("src/it/unibo/paw/sources/archivioVolo.xml");
         FileWriter fw = new FileWriter(new File("src/it/unibo/paw/sources/Volo.txt"));

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

         Set<String> voli = new HashSet<String>();
               
               // Per ogni archivio analizziamo le prenotazioni
               NodeList nListPrenotazioni = doc.getElementsByTagName("prenotazione");
               
               // per ogni prenotazione
               for (int temp1 = 0; temp1 < nListPrenotazioni.getLength(); temp1++) {
                   Node nNodePrenotazione = nListPrenotazioni.item(temp1);

                   if (nNodePrenotazione.getNodeType() == Node.ELEMENT_NODE) {
                      Element eElementPrenotazione = (Element) nNodePrenotazione;
                      
                      String codice = eElementPrenotazione.getElementsByTagName("codice").item(0).getTextContent();
                      System.out.println("Analizzo " + codice);
                      
                      // Controlliamo le prime caratteristiche
                      String tipo_volo = eElementPrenotazione.getElementsByTagName("tipo_volo").item(0).getTextContent();
                      String compagnia = eElementPrenotazione.getElementsByTagName("nome_compagnia").item(0).getTextContent();
                      String aeroporto_partenza = eElementPrenotazione.getElementsByTagName("aeroporto_partenza").item(0).getTextContent();
                      int mese = Integer.parseInt(eElementPrenotazione.getElementsByTagName("data_partenza").item(0).getChildNodes().item(1).getTextContent());
                      
                      if(tipo_volo.compareTo("Andata-Ritorno")==0 && compagnia.compareTo("Ryanair")==0 && aeroporto_partenza.compareTo("Bologna")==0
                    		  &&
                    	mese >= 6 && mese <=8 && !voli.contains(codice)){voli.add(codice);}
                      }
                   }
         
         // Finita la costruzione della lista
         fw.write("Lista di voli trovati:\n");
         for(String id : voli) {fw.write(id + "\n");}
         fw.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}