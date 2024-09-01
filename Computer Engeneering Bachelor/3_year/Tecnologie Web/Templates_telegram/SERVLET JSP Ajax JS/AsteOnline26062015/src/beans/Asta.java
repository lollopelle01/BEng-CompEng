package beans;

import java.util.Random;

public class Asta {
	
	private Utente bestBidder;
	private String nome;
	private double bestBid;
	private boolean isOpen;
	private Thread timer;
	
	public Asta() {
		bestBidder = null;
		nome = "Asta "+new Random().nextInt(1000);
		bestBid = 0;
		isOpen = true;
	}
	
	public Utente getBestBidder() {
		return bestBidder;
	}
	public String getNome() {
		return nome;
	}
	public double getBestBid() {
		return bestBid;
	}
	public boolean isOpen() {
		return isOpen;
	}
	
	public boolean makeOffer(double offer, Utente bidder) {
		if (bestBid == 0) {
			timer = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isOpen = false;
				}
			};
			timer.start();
		}
		if (offer > bestBid) {
			bestBid = offer;
			bestBidder = bidder;
			return true;
		}
		else 
			return false;
	}
	
	

}
