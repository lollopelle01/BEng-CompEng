package edicecream.persistence;

import java.util.Set;

import edicecream.model.IceCreamKind;

public interface IceCreamKindsRepository {
	Set<String> getKindNames();
	IceCreamKind getIceCreamKind(String kind);
}
