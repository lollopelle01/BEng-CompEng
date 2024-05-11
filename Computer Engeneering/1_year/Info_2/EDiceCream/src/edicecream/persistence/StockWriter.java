package edicecream.persistence;

import java.io.Writer;

public interface StockWriter {
	void printStock(Writer writer, IceCreamRepository repo);
}