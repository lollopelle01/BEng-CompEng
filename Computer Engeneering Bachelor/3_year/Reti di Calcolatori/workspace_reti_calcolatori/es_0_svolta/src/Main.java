

public class Main {
	public static void main(String[] args) {
		 Consumatore consumatore=new Consumatore("/Users/pelle/Desktop/Prova1.txt");
		 Produttore produttore=new Produttore("Prova1");
		
		produttore.start();
		try {
			produttore.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		consumatore.start();
	}
}
