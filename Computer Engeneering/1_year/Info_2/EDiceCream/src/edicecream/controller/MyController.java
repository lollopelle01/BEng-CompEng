package edicecream.controller;


import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edicecream.model.IceCream;
import edicecream.model.IceCreamKind;
import edicecream.model.IceCreamShop;
import edicecream.persistence.IceCreamKindsRepository;
import edicecream.persistence.IceCreamRepository;
import edicecream.persistence.StockWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyController implements Controller{
	private IceCreamKindsRepository ikr;
	private IceCreamRepository ir;
	private IceCreamShop is;
	private StockWriter stockWriter;

	public MyController(IceCreamKindsRepository ikr, IceCreamRepository ir, IceCreamShop is, StockWriter stockWriter) {
		this.ikr=ikr;
		this.ir=ir;
		this.is=is;
		this.stockWriter=stockWriter;
	}
	@Override
	public Set<String> getFlavors() {
		return new TreeSet<String>(this.ir.getFlavors());
	}

	@Override
	public Set<String> getKindNames() {
		return new TreeSet<String>(this.ikr.getKindNames());
	}

	private IceCream createIceCream(String kind, List<String> flavors) {
		IceCreamKind icKind = ikr.getIceCreamKind(kind);
		if (icKind == null) {
			return null;
		}

		if (flavors.size() > icKind.getMaxFlavors()) {
			return null;
		}
		
		IceCream rv = new IceCream(icKind, flavors);

		for (String fl : flavors) {
			if (ir.getAvailableQuantity(fl) < rv.getFlavorWeight()) {
				return null;
			}
		}

		return rv;
	}
	
	@Override
	public boolean addIceCream(String kind, List<String> flavors) {
		IceCream ic = createIceCream(kind, flavors);
		if (ic == null) {
			return false;
		}

		for (String fl : flavors) {
			ir.removeQuantity(fl, ic.getFlavorWeight());
		}
		is.addIceCream(ic);

		return true;
	}

	private void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void printReport() {
		try(Writer w=new FileWriter("StockGiornaliero.txt")){
			this.stockWriter.printStock(w, ir);
		}
		catch(IOException e) {
			alert("Errore", "Scrittura Report", "Impossibile scrivere il file di report.");
		}

	}

	@Override
	public int getMaxFlavors(String flavor) {
		return this.ikr.getIceCreamKind(flavor).getMaxFlavors();
	}

	@Override
	public String getIceCreamStatus() {
		StringBuilder sb =new StringBuilder();
		sb.append("Totale gelati venduti: ");
		sb.append(is.getIceCreamCount()+"\n");
		for(String gusto: this.getFlavors()) {
			sb.append("Venduti "+is.getTotalFlavorConsumption(gusto)+"gr di "+gusto+"\n");
		}
		sb.append("Totale incasso: "+is.getTotalIncome()+" euro\n");
		return sb.toString();
	}

}
