package it.unibo.paw;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Template_ErrorHandler extends DefaultHandler{
	
	public Template_ErrorHandler() {
		super();
	}
	
	public void error (SAXParseException e) {
		System.out.println("Parsing error: "+e.getMessage());
	}
	public void warning (SAXParseException e) {
		System.out.println("Parsing problem: "+e.getMessage());
	}
	public void fatalError (SAXParseException e) {
		System.out.println("Parsing error: "+e.getMessage());
		System.out.println("Cannot continue.");
		System.exit(1);
	}
}
