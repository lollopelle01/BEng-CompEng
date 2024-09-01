package Beans;

public class Testo {
   private String testo;
   private int x;
   private int y;
   private String filename;
   
   public Testo(String testo, int x, int y, String filename) {
	   this.testo = testo;
	   this.x = x;
	   this.y = y;
	   this.filename = filename;
   }
   
   public String getTesto() {
	   return this.testo;
   }
   
   public void setTesto(String testo) {
	  this.testo = testo;
   }
   

   public String getFilename() {
	   return this.filename;
   }
   
   public void setFilename(String filename) {
	  this.filename = filename;
   }
   
   public int getX() {
	   return x;
   }
   
   public void setX(int x) {
	  this.x = x;
   }
   
   public int getY() {
	   return y;
   }
   
   public void setY(int y) {
	  this.y = y;
   }
}
