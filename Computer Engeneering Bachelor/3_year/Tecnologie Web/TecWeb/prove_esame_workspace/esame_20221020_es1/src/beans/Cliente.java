package beans;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class Cliente {
    private ArrayList<Drink> drinks;
    private HttpSession sessione;

    public Cliente() { }

    public Cliente( HttpSession sessione, ArrayList<Drink> drinks) {
        this.drinks = drinks;
        this.sessione = sessione;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    public void addDrink(Drink d){
        this.drinks.add(d);
    }

    public float calcolaCosto(){
        float result = 0;
        for (Drink d : drinks){
            if(d.isConsegnato()){result += d.getPrezzo();}
        }
        return result;
    }

    public HttpSession getSessione() {
        return sessione;
    }

    public void setSessione(HttpSession sessione) {
        this.sessione = sessione;
    }

    @Override
    public String toString(){
        String listaDrink = "[ ";
        for(Drink d : this.drinks){
            listaDrink += d.getNome();
        }

        return "L'utente " + this.getSessione().getId() + " ha acquistato: " + listaDrink + " ]";
    }
}
