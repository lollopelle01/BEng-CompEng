package it.esame;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXContentHandler extends DefaultHandler {
	
	private List<Scelta> scelte = new ArrayList<Scelta>();
	private Scelta curScelta;
	private Capo curCapo;
	
	boolean inCapoSelected = false;
	boolean inFoto = false;
	boolean inDescrizione = false;
	boolean inPrezzo = false;
	
	public void startElement (String namespaceURI, String localName, String rawName, Attributes atts) { 
		//System.out.println("AddressListContentHandler.startElement   namespaceURI=" + namespaceURI + " localName=" + localName + " rawName=" +rawName +" atts="+atts);
		if (localName.equals("scelta")){
			curScelta = new Scelta();
			curScelta.setTipo(atts.getValue("type"));
		}
		else if(localName.equals("capo") && atts.getValue("selected").equals("yes")){
			curCapo = new Capo();
			inCapoSelected = true;
		}
		else if(localName.equals("foto")){
			inFoto = true;
		}
		else if(localName.equals("descrizione")){
			inDescrizione = true;
		}
		else if (localName.equals("prezzo")) {
			inPrezzo = true;
		}
	} 

	public void characters (char ch[], int start, int length) {
		//System.out.println("AddressListContentHandler.characters   start=" + start + " length=" + length + " ch=" +new String(ch,start,length));
		if (inCapoSelected && inFoto)
			curCapo.setImage(new String(ch, start, length));
		
		else if (inCapoSelected && inDescrizione)
			curCapo.setDescription(new String(ch, start, length));
		
		else if (inCapoSelected && inPrezzo)
			curCapo.setPrice(Double.parseDouble(new String(ch, start, length)));
		
		
		
//		if( inFirstName ){
//			firstName = new String(ch,start,length);
//		}
//		else if( inLastName ){
//			lastName = new String(ch,start,length);
//		}
//		else if( inTelephone ){
//			Telephone = new String(ch,start,length);
//		}
	} 

	public void endElement(String namespaceURI, String localName, String qName) {
		//System.out.println("AddressListContentHandler.endElement   namespaceURI=" + namespaceURI + " localName=" + localName + " qName=" +qName);
		if(localName.equals("scelta")){
			scelte.add(curScelta);
		}
		else if(localName.equals("capo")){
			if (inCapoSelected)
				curScelta.addCapo(curCapo);
			inCapoSelected = false;
		}
		else if (localName.equals("foto")) {
			inFoto = false;
		}
		else if (localName.equals("descrizione")) {
			inDescrizione = false;
		}
		else if (localName.equals("prezzo")) {
			inPrezzo = false;
		}
		
//		else if(localName.equals("Telephone")){
//			inTelephone = false;
//			if( firstName.startsWith("Don")){
//				donTel.addElement(Telephone);
//			}
//		}
//		else if (localName.equals("Information")){
//			if( !mmFound && firstName!=null && lastName!=null ){ // controllo non necessario per documenti XML validi
//				if( firstName.equals("Mickey") && lastName.equals("Mouse") ){
//					mmFound = true;
//				}
//				else{
//					peoplePreMM++;
//				}
//			}
//		}
	}
	
//	private int ignorableWhitespace = 0;
//	@Override
//	public void ignorableWhitespace(char[] ch, int start, int length)
//			throws SAXException {
//		ignorableWhitespace += length;
//	}
//	public int getIgnorableWhitespace(){
//		return ignorableWhitespace;
//	}
	
	public List<Scelta> getScelte() {
		return scelte;
	}
	
}	
