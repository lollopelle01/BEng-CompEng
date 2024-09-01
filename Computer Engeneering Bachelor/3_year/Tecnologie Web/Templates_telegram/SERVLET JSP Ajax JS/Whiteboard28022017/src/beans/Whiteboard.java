package beans;

import java.util.ArrayList;
import java.util.List;

public class Whiteboard {
	
	List<String> lines = new ArrayList<>();
	private int version;
	
	public Whiteboard() {
		version = 0;
	}
	
	public synchronized void reset() {
		version = 0;
		lines = new ArrayList<>();
	}
	
	
	public synchronized boolean tryAddLine(String line, int version) {
		if (version == this.version) {
			lines.add(line);
			this.version++;
			return true;
		} else
			return false;
	}
	
	public List<String> getLinesFromVersion(int version) {
		List<String> result = new ArrayList<>();
		for (int i=version; i<this.version; i++) {
			result.add(lines.get(i));
		}
		return result;
	}
	
	
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
