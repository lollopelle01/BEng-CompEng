package it.esame;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ErrorHandler extends DefaultHandler{
	
	public void error (SAXParseException e) {
		System.out.println("Parsing error at line "+e.getLineNumber()+": "+e.getMessage());
	}
	
	public void warning (SAXParseException e) {
		System.out.println("Parsing warning at line "+e.getLineNumber()+": "+e.getMessage());
		System.out.println("XML document NOT valid");
	}
	
	public void fatalError (SAXParseException e) {
		System.out.println("Parsing fatal error at line "+e.getLineNumber()+": "+e.getMessage());
		System.out.println("XML document NOT well-formed");
		System.out.println("Cannot continue.");
		System.exit(1);
	}
	
}
