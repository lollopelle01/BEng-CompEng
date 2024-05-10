package electriclife.ui.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import electriclife.model.Bolletta;
import electriclife.model.Tariffa;
import electriclife.persistence.BadFileFormatException;
import electriclife.persistence.BollettaWriter;
import electriclife.persistence.TariffeReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyController implements Controller{
	private HashMap<String, Tariffa> mappaTariffe=new HashMap<>();
	
	@Override
	public void leggiTariffe(TariffeReader tariffaReader) throws IOException, BadFileFormatException {
		for(Tariffa t: tariffaReader.caricaTariffe()) {
			this.mappaTariffe.put(t.getNome(), t);
		}
	}

	@Override
	public ObservableList<Tariffa> getTariffe() {
		var result= FXCollections.observableArrayList(this.mappaTariffe.values());
		FXCollections.sort(result);
		return result;
	}

	@Override
	public Bolletta creaBolletta(LocalDate date, String nomeTariffa, int consumo) {
		return this.mappaTariffe.get(nomeTariffa).creaBolletta(date, consumo);
	}

	@Override
	public void stampaBolletta(Bolletta b, BollettaWriter bollettaWriter) {
		bollettaWriter.stampaBolletta(b);
	}

}
