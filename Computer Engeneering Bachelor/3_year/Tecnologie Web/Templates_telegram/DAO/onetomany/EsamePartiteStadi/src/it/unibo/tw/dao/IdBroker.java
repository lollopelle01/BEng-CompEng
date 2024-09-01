package it.unibo.tw.dao;

public interface IdBroker {
	
	boolean createSequence();
	boolean deleteSequence();
	int newId();

}
