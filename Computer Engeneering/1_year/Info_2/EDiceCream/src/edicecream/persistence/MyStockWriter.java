package edicecream.persistence;

import java.io.PrintWriter;
import java.io.Writer;

public class MyStockWriter implements StockWriter {
	/* (non-Javadoc)
	 * @see edicecream.persistence.StockWriter#printStock(java.io.Writer, edicecream.persistence.IceCreamRepository)
	 */
	@Override
	public void printStock(Writer writer, IceCreamRepository repo) {
		PrintWriter pw = new PrintWriter(writer);
		pw.println("Rimanenze per gusto:");
		for (String fl : repo.getFlavors()) {
			pw.println(fl + ": " + repo.getAvailableQuantity(fl));
		}
	}
}
