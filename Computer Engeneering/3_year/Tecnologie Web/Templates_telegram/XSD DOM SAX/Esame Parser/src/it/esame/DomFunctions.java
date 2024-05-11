package it.esame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomFunctions {
	
	// FUNZIONI SEMPRE UTILI --------------------------------------------------------------
	
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

	public static void printDom(Document document) throws TransformerException{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    
	    Source source = new DOMSource(document);
	    Result output = new StreamResult(System.out);
	    transformer.transform(source, output);
	}
	
	// FUNZIONI RICHIESTE --------------------------------------------------------------	

	public static int getPeopleAmount(Document domDocument){
		NodeList informationList = domDocument.getElementsByTagName("Information");
		return informationList.getLength();
	}

	public static int getPeoplePreMM(Document domDocument){
		int proplePreMM = 0;
		boolean mmFound = false;
	
		NodeList informationList = domDocument.getElementsByTagName("Information");
		for(int i=0; !mmFound && i<informationList.getLength(); i++){
			Node information = informationList.item(i);
			
			// cerco "Full_name"
			NodeList infoChildrenNodes = information.getChildNodes();
			Node fullName = null;
			for(int j=0; fullName==null && j<infoChildrenNodes.getLength(); j++){
				Node node = infoChildrenNodes.item(j);
				if( node.getNodeName().equals("Full_name")){
					fullName = node;
				}
			}
			NodeList fullNameList = fullName.getChildNodes();
			
			// cerco "First_name" e "Last_name"
			String firstName = null, lastName = null;
			for(int j=0; j<fullNameList.getLength(); j++){
				Node el = fullNameList.item(j);
				if( el.getLocalName()!=null ){
					if ( el.getLocalName().equals("First_name")){
						firstName = el.getTextContent();
					}
					else if(el.getLocalName().equals("Last_name")){
						lastName = el.getTextContent();
					}
				}
			}
			
			//if( firstName!=null && lastName!=null ){
				if( firstName.equals("Mickey") && lastName.equals("Mouse") ){
					mmFound = true;
				}
			//}
			else{
				proplePreMM++;
			}
		}
		
		return proplePreMM;
	}

	public static Vector<String> getTel(String startsWith, Document domDocument){
		Vector<String> tel = new Vector<String>();
		
		NodeList informationList = domDocument.getElementsByTagName("Information");
		//System.out.println("getPeoplePreMM informationList.getLength()="+informationList.getLength());
		for(int i=0; i<informationList.getLength(); i++){
			boolean found = false;
			
			Node information = informationList.item(i);
	
			// cerco "Full_name"
			NodeList infoChildrenNodes = information.getChildNodes();
			Node fullName = null;
			for(int j=0; fullName==null && j<infoChildrenNodes.getLength(); j++){
				Node node = infoChildrenNodes.item(j);
				if( node.getNodeName().equals("Full_name")){
					fullName = node;
				}
			}
			NodeList fullNameList = fullName.getChildNodes();
			
			// cerco "First_name"
			for(int j=0; j<fullNameList.getLength(); j++){
				Node el = fullNameList.item(j);if( el.getLocalName()!=null ){
					if ( el.getLocalName().equals("First_name")){
						if(el.getTextContent().startsWith(startsWith)){
							found = true;
						}
					}
				}
			}
			
			if(found){
				// cerco "Telephone"
				boolean telephone = false;
				for(int j=0; !telephone && j<infoChildrenNodes.getLength(); j++){
					Node node = infoChildrenNodes.item(j);
					if( node.getNodeName().equals("Telephone")){
						telephone = true;
						tel.addElement(node.getTextContent());
					}
				}
			}
		}
		
		return tel;
	}

	public static void setTel(String firstName, String lastName, String newTelephone, Document domDocument){
		NodeList informationList = domDocument.getElementsByTagName("Information");
		//System.out.println("getPeoplePreMM informationList.getLength()="+informationList.getLength());
		for(int i=0; i<informationList.getLength(); i++){
			boolean foundFirstName = false;
			boolean foundLastName = false;
			
			Node information = informationList.item(i);
	
			// cerco "Full_name"
			NodeList infoChildrenNodes = information.getChildNodes();
			Node fullName = null;
			for(int j=0; fullName==null && j<infoChildrenNodes.getLength(); j++){
				Node node = infoChildrenNodes.item(j);
				if( node.getNodeName().equals("Full_name")){
					fullName = node;
				}
			}
			NodeList fullNameList = fullName.getChildNodes();
			
			// cerco "First_name" e "Last_name"
			for(int j=0; j<fullNameList.getLength(); j++){
				Node el = fullNameList.item(j);
				if( el.getLocalName()!=null ){
					if ( el.getLocalName().equals("First_name")){
						if(el.getTextContent().equals(firstName)){
							foundFirstName = true;
						}
					}
					else if ( el.getLocalName().equals("Last_name")){
						if(el.getTextContent().equals(lastName)){
							foundLastName = true;
						}
					}
				}
			}
			
			if( foundFirstName && foundLastName ){
				// cerco "Telephone"
				boolean telephone = false;
				for(int j=0; !telephone && j<infoChildrenNodes.getLength(); j++){
					Node node = infoChildrenNodes.item(j);
					if( node.getNodeName().equals("Telephone")){
						telephone = true;
						node.setTextContent(newTelephone);
					}
				}
				if( ! telephone ){
					Element telEl = domDocument.createElement("Telephone");
					telEl.setTextContent(newTelephone);
					information.appendChild(telEl);
				}
			}
		}
	}

	public static float getGuadagnoFuturo(Document doc, String nomeReparto){
		float res = 0.0F;
		
		// cerco il reparto con nome "nomeReparto"
		NodeList repartoList = doc.getElementsByTagName("Reparto");
		Node reparto = null;
		for(int i=0; reparto==null && i<repartoList.getLength(); i++){
			Node repartoTemp = repartoList.item(i);
			NamedNodeMap attributes = repartoTemp.getAttributes();
			Node nomeRepartoNode = attributes.getNamedItem("NomeReparto");
			if( nomeRepartoNode.getTextContent().equals(nomeReparto) ){
				reparto = repartoTemp;
			}
		}
		
		if( reparto != null ){
			NodeList merceList = reparto.getChildNodes();
			
			// itero su ciascun nodo "Merce"
			for(int i=0; i<merceList.getLength(); i++){
				Node merce = merceList.item(i);
				
				// cerco i nodi "PrezzoMerce" e "QuantitaMerce"
				Float prezzoMerce = null;
				Integer quantitaMerce = null;
				NodeList merceChildNodes = merce.getChildNodes();
				for(int j=0; j<merceChildNodes.getLength() && (prezzoMerce==null || quantitaMerce==null ); j++){
					Node node = merceChildNodes.item(j);
					if( node.getNodeName().equals("PrezzoMerce")){
						prezzoMerce = Float.parseFloat(node.getTextContent());
					}
					else if( node.getNodeName().equals("QuantitaMerce")){
						quantitaMerce = Integer.parseInt(node.getTextContent());
					}
				}
				
				// una volta ottenute le informazioni che mi servono,
				// calcolo il risultato parziale
				res += ( prezzoMerce * quantitaMerce );
			}
		}
		
		return res;
	}
	
	public static List<Scelta> getScelte(Document document) {
		List<Scelta> result = new ArrayList<Scelta>();
		
		NodeList scelte = document.getElementsByTagName("scelta");
		for (int i=0; i<scelte.getLength(); i++) {
			Scelta scelta = new Scelta();
			scelta.setTipo(getAttributeByName(scelte.item(i), "type"));
			
			NodeList capi = scelte.item(i).getChildNodes();
			for (int j=0; j<capi.getLength(); j++) {
				if (capi.item(j).getNodeName().equals("capo") && getAttributeByName(capi.item(j), "selected").equals("yes")) {
					Capo capo = new Capo();
					capo.setDescription(getNodeByName(capi.item(j), "descrizione").getTextContent());
					capo.setImage(getNodeByName(capi.item(j), "foto").getTextContent());
					try {
						capo.setPrice(Double.parseDouble(getNodeByName(capi.item(j), "prezzo").getTextContent()));
					} catch(Exception e) {
						System.out.println("Documento non valido");
						e.printStackTrace();
						capo.setPrice(0);
					}
					
					scelta.addCapo(capo);
				}
			}
			
			result.add(scelta);
		}
		
		return result;
	}

}
