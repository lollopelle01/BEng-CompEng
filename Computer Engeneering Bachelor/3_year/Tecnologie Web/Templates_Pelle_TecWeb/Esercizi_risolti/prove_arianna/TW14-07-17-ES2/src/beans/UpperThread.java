package beans;

public class UpperThread extends Thread {

	public int maxchars;
	public int converted = 0;
	public String textinit;
	public String textChanged;
	
	//get e set
	public int getMaxchars() {
		return this.maxchars;
	}
	public void setMaxchars(int m) {
		this.maxchars = m;
	}
	
	public void setText(String init) {
		this.textinit = init;
	}
	
	public int getConverted() {
		return this.converted;
	}
	
	public String getTextchanged() {
		return this.textChanged;
	}
	
	//run
	@Override
	public void run() {
		for(int i=0; i<this.maxchars; i++) {
			
			if(Character.isLowerCase(this.textinit.charAt(i))) {
				Character.toUpperCase(this.textinit.charAt(i));
				this.converted++;
			}
		}
		this.textChanged = this.textinit;
	}
	
	
}
