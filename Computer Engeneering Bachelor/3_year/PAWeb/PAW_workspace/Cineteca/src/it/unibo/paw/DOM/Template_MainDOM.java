package it.unibo.paw.DOM;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import it.unibo.paw.Template_ErrorHandler;

public class Template_MainDOM {
	
	private static int audiovisiviBN = 0;
	private static int pellicole16 = 0;
	
	// da fa
	public static void main(String[] args){
		
		audiovisiviBN = 0;
		pellicole16 = 0;

      try {
         // Definiamo .xml da parsare
         File inputFile = new File("src/it/unibo/paw/sources/Cineteca.xml");

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
         NodeList filmNodeList = doc.getElementsByTagName("film");

         // Esploriamo la lista di nodi per analizzare oggetto
         for (int temp = 0; temp < filmNodeList.getLength(); temp++) {
            Node filmNode = filmNodeList.item(temp);

            if (filmNode.getNodeType() == Node.ELEMENT_NODE) {
               Element filmElement = (Element) filmNode.getChildNodes().item(0);
//               System.out.println(filmElement.getChildNodes().item(4).getTextContent());
               
               if(filmElement.getLocalName().equals("audiovisivo")) {
            	   if(filmElement.getChildNodes().item(5).getTextContent().equals("BN")
            			   	&&
            	      filmElement.getChildNodes().item(10).getTextContent().equals("si"))	{audiovisiviBN++;}
               }
               else { //pellicola
            	   if(filmElement.getChildNodes().item(2).getTextContent().equals("si")
           			   	&&
           	      filmElement.getChildNodes().item(3).getTextContent().equals("16mm"))	{pellicole16++;}
               }
            }
         }
         
         FileWriter fw = new FileWriter("Cineteca.txt");
         fw.write("audiovisiviBN: " + audiovisiviBN);
         fw.write("\npellicole16: " + pellicole16);
         fw.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
	
	public int getAudiovisiviBN(){
		//main(null);
		return this.audiovisiviBN;
	}
	
	public int getPellicole16(){
		//main(null);
		return this.pellicole16;
	}
}