package zannopoll.ui.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import zannopoll.model.Sesso;
import zannopoll.model.SondaggioPercentuale;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.SondaggioReader;

public class MyController extends Controller {

	public MyController(SondaggioReader myReader, Reader reader, DialogManager dialogManager) throws IOException, BadFileFormatException {
		super(myReader, reader, dialogManager);
	}

	@Override
	public Optional<SondaggioPercentuale> getSondaggioPercentuale(int minAge, int maxAge, Optional<Sesso> sex) {
		var result= this.getSondaggioPercentuale().getSondaggioFiltrato(minAge, maxAge, sex);
		if(!result.isPresent())
			this.alert("erore lettura sondaggi", "non ci sono sondaggi", "");
		return result;
	}

}
