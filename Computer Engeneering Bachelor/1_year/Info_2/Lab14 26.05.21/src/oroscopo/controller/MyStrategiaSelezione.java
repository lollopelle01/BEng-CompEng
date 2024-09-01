package oroscopo.controller;

import java.util.List;
import java.util.Random;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezione implements StrategiaSelezione{

	@Override
	public Previsione seleziona(List<Previsione> previsioni, SegnoZodiacale segno) {
		Random r=new Random();
		Previsione p;
		do {
			p=previsioni.get(r.nextInt(previsioni.size()));
		}
		while(!p.validaPerSegno(segno));
		return p;
	}

}
