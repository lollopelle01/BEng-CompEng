import java.io.Serializable;

// pellegrino lorenzo 0000971455 compito 1
public class Riga implements Serializable{
    private String id=null, brand=null, folder=null;
    private int gg, mm, aaaa;

    public Riga(String id, String brand, String folder, int gg, int mm, int aaaa) {
        this.id = id;
        this.brand = brand;
        this.folder = folder;
        this.gg = gg;
        this.mm = mm;
        this.aaaa = aaaa;
    }

    
    public synchronized String getId() {
        return id;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }


    public synchronized String getBrand() {
        return brand;
    }

    public synchronized void setBrand(String brand) {
        this.brand = brand;
    }


    public synchronized String getFolder() {
        return folder;
    }

    public synchronized void setFolder(String folder) {
        this.folder = folder;
    }


    public synchronized int getGg() {
        return gg;
    }

    public synchronized void setGg(int gg) {
        this.gg = gg;
    }

    public synchronized int getMm() {
        return mm;
    }

    public synchronized void setMm(int mm) {
        this.mm = mm;
    }

    public synchronized int getAaaa() {
        return aaaa;
    }

    public synchronized void setAaaa(int aaaa) {
        this.aaaa = aaaa;
    }




    public void stampa(){
        System.out.println("/--------------------------------------------------------/");
        System.out.println(id);
        System.out.println(gg + "/" + mm + "/" + aaaa);
        System.out.println(brand);
        System.out.println(folder);
    }
}