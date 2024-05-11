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

    public Frazione get(int index) {
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
            sj.add(get(i).toString());
        }
        return sj.toString();
    }

    public FractionCollection sum(FractionCollection that) {
        if (this.size() != that.size)
            return null;
        else {
            FractionCollection c = new FractionCollection(that.size());
            for (int i = 0; i < this.size(); i++) {
                c.put(this.get(i).sum(that.get(i)));
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
                c.put(this.get(i).mul(that.get(i)));
            }
            return c;
        }
    }
}
