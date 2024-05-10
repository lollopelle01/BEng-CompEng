package fractioncollection;

import frazione.Frazione;

import java.util.StringJoiner;

public class FractionCollection {
    private static final int DEFAULT_PHYSICAL_SIZE = 10;
    private static final int GROWTH_FACTOR = 2;
    private Frazione[] x;
    private int size;

    public FractionCollection() {
        this(DEFAULT_PHYSICAL_SIZE);
    }
    public FractionCollection(int size) {
        x = new Frazione[size];
        this.size = 0;
    }

    public FractionCollection(Frazione[] fs) {
        x = new Frazione[fs.length];
        System.arraycopy(fs, 0, x, 0, fs.length);
        int size = 0;
        for (Frazione f : fs) {
            if (f != null)
                size++;
        }
        this.size = size;
    }

    public Frazione getFraction(int index) {
        if(index > size())
            throw new ArrayIndexOutOfBoundsException();
        else
            return x[index];
    }

    public int size() {
        return size;
    }

    public void put(Frazione f){
        if(size()+1<=this.x.length) {
            x[size] = f;
            this.size++;
        }
        else {
            var temp_size = size();
            var y = new Frazione[x.length*GROWTH_FACTOR];
            int i;
            for (i = 0; i < this.size(); i++) {
                y[i] = x[i];
            }
            y[i] = f;
            x = new Frazione[x.length*GROWTH_FACTOR];
            System.arraycopy(y, 0, x, 0, y.length);
            this.size = ++temp_size;
        }
    }

    public void remove(int index){
        if(index>x.length)
            throw new ArrayIndexOutOfBoundsException();
        else{
            x[index] = null;
            var y = new Frazione[x.length];
            int i = 0;
            int j = 0;
            while (i<x.length){
                if(x[i]!= null){
                    y[j] = x[i];
                    j++;
                }
                i++;
            }
            this.size--;
            x = new Frazione[x.length];
            System.arraycopy(y, 0, x, 0, y.length);
        }
    }


    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < this.size(); i++) {
            sj.add(getFraction(i).toString());
        }
        return sj.toString();
    }

    public FractionCollection sum(FractionCollection that) {
        if (this.size() != that.size)
            return null;
        else {
            FractionCollection c = new FractionCollection(that.size());
            for (int i = 0; i < this.size(); i++) {
                c.put(this.getFraction(i).sum(that.getFraction(i)));
            }
            return c;
        }

    }

    public FractionCollection mul(FractionCollection that){
        if(this.size() != that.size())
            return null;
        else{
            FractionCollection c = new FractionCollection(that.size());
            for (int i = 0; i < that.size(); i++) {
                c.put(this.getFraction(i).mul(that.getFraction(i)));
            }
            return c;
        }
    }
}







PARTE 2
package fractioncollection;
import java.util.StringJoiner;

import frazione.*;

public class FractionCollection {
	private int Default_phisical_size=10;
	private int Grouth_factor=2;
	private Frazione[] innerContainer;
	private int size;
	 
	public FractionCollection() {
		this.size=Default_phisical_size;
		this.innerContainer=new Frazione[this.size];
	}
	public FractionCollection(int k) {
		this.size=0;
		this.innerContainer=new Frazione[k];
	}
	public FractionCollection(Frazione[] array) {
		this.size=0;
		this.innerContainer=new Frazione[array.length];
		for(int i=0; i<array.length; i++) {
			if(array[i]!=null) {
				this.innerContainer[this.size]=array[i];
				this.size++;
			}
		}
	}
	
	public Frazione get(int k) {
		if(k>=0 && k<=this.size)
			return innerContainer[k];
		else
			return null;
	}
	public int size() { 
		return size;
	}
	public void put(Frazione f) {
		if(this.size+1<=this.innerContainer.length) {
			this.innerContainer[this.size]=f;
			this.size++;
		}
		else {
			Frazione[] nuovo=new Frazione[Grouth_factor*this.innerContainer.length];
			int i;
			for(i=0; i<this.size; i++) {
				nuovo[i]=this.innerContainer[i];
			}
			nuovo[i]=f;
			this.innerContainer=nuovo;
			this.size=i;
		}
	}
	public void remove(int k) {
		this.size=0;
		Frazione[] temp=new Frazione[this.innerContainer.length-1];
		for(int i=0; i<this.size; i++) {
			if(i!=k) {
				temp[this.size]=this.innerContainer[this.size];
				this.size++;
			}
		}
		this.innerContainer=temp;
	}
	public String toString() {
		StringJoiner sj=new StringJoiner(",", "[", "]");
		for(int i=0; i<this.size(); i++) {
			sj.add(get(i).toString());
		}
		return sj.toString();
	}
	
	public FractionCollection sum(FractionCollection c) {
		FractionCollection result;
		if(this.size==c.size) {
			result=new FractionCollection(this.size);
			for(int i=10; i<c.size() && c.get(i)!=null && this.get(i)!=null; i++) {
				result.put((c.get(i)).sum(this.get(i)));
			}
			return result;
		}
		else
			return null;
	}
	public FractionCollection mul(FractionCollection c) {
		FractionCollection result=null;
		if(this.size==c.size) {
			result=new FractionCollection(this.size);
			for(int i=0; i<c.size(); i++) {
				result.put((c.get(i)).mul(this.get(i)));
			}
		}
		return result;
	}
	public FractionCollection div(FractionCollection c) {
		FractionCollection result=null;
		if(this.size==c.size) {
			result=new FractionCollection(this.size);
			for(int i=0; i<c.size(); i++) {
				result.put((c.get(i)).div(this.get(i)));
			}
		}
		return result;
	}
	public FractionCollection sub(FractionCollection c) {
		FractionCollection result=null;
		if(this.size==c.size) {
			result=new FractionCollection(this.size);
			for(int i=0; i<c.size(); i++) {
				result.put((c.get(i)).sub(this.get(i)));
			}
		}
		return result;
	}

}

