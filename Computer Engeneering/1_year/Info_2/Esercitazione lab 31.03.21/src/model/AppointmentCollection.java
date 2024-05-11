package model;


public class AppointmentCollection {
	private int DEFAULT_GROWTH_FACTOR=2;
	private int DEFAULT_PHYSICAL_SIZE=10;
	private Appointment[] l;
	private int size;
	
	public AppointmentCollection() {
		 this.l=new Appointment[DEFAULT_PHYSICAL_SIZE];
		 this.size=0;
	}
	public AppointmentCollection(int capacity) {
		this.l=new Appointment[capacity];
		this.size=0;
	}
	
	public Appointment get(int k) {
		return this.l[k];
	}
	public int indexfOf(Appointment a) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).equals(a)) 
				return i;
		}
		return -1;
	}
	public void remove(int index){
	        if(index>l.length)
	            throw new ArrayIndexOutOfBoundsException();
	        else{
	            l[index] = null;
	            var y = new Appointment[l.length];
	            int i = 0;
	            int j = 0;
	            while (i<l.length){
	                if(l[i]!= null){
	                    y[j] = l[i];
	                    j++;
	                }
	                i++;
	            }
	            this.size--;
	            l = new Appointment[l.length];
	            System.arraycopy(y, 0, l, 0, y.length);
	        }
	    }
	public int size() {
		return this.size;
	}
	public void add(Appointment a){
		if (this.size() >= this.l.length) {
			Appointment[] t = new Appointment[this.l.length * DEFAULT_GROWTH_FACTOR];
			for (int i = 0; i < this.size(); i++) 
				t[i] = this.get(i);
			this.l = t;
		}
		this.l[this.size++] = a;
        }
    }
