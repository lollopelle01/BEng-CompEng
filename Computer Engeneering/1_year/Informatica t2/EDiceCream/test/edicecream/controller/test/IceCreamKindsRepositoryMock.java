package edicecream.controller.test;

import java.util.HashMap;
import java.util.Set;

import edicecream.model.IceCreamKind;
import edicecream.persistence.IceCreamKindsRepository;

public class IceCreamKindsRepositoryMock implements IceCreamKindsRepository {
	private HashMap<String,IceCreamKind> data;

	public IceCreamKindsRepositoryMock() {
		data = new HashMap<>();
		data.put("Cono piccolo", new IceCreamKind("Cono piccolo", 1.5f, 1, 30));
		data.put("Cono medio", new IceCreamKind("Cono medio", 2f, 2, 30));
		data.put("Cono grande", new IceCreamKind("Cono grande", 2.5f, 3, 20));
	}
	public IceCreamKind getIceCreamKind(String kind) {
		return data.get(kind);	
	}

	@Override
	public Set<String> getKindNames() {
		return data.keySet();
	}
}
