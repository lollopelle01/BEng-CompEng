package controller;

import media.Media;
import media.collection.MediaCollection;
import media.filters.*;

public class MediaController {
	private MediaCollection allMedias=null;
	
	public MediaCollection getAll() {
		var result=new MediaCollection();
		for(int i=0; i<this.allMedias.size(); i++) {
			result.add(allMedias.get(i));
		}
		return result;
	}
	public boolean add(Media m) {
		int oldSize = this.allMedias.size();
		for (int i = 0; i < this.allMedias.size(); i++) {
			if(allMedias.get(i).equals(m)) return false;
		}
		this.allMedias.add(m);
		return (oldSize == (this.allMedias.size() - 1));
	}
	public boolean remove(Media m) {
		int oldSize = this.allMedias.size();
		for (int i = 0; i < this.allMedias.size(); i++) {
			if(allMedias.get(i).equals(m)) this.allMedias.remove(i);
		}
		return (oldSize == (this.allMedias.size() + 1));
	}
	public MediaController() {
		this.allMedias=new MediaCollection();
	}
	public MediaCollection find(Filter f) {
		var result=new MediaCollection();
		for(int i=0; i<this.allMedias.size(); i++) {
			if(f.filter(this.allMedias.get(i)))
				result.add(this.allMedias.get(i));
		}
		return result;
	}
}

