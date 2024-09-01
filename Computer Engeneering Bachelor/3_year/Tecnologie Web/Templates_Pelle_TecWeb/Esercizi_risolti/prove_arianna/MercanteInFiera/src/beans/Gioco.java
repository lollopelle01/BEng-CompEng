package beans;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gioco {
	
	int vincitore;
	boolean vendita;
	boolean giocoCominciato;
	List<Utente> utenti;
	Instant startTime;
	Random r;
	List<String> messaggio;
	
	public Gioco() {
		super();
		this.r = new Random();
		this.vincitore = r.nextInt(30)+1;
		this.vendita = false;
		this.giocoCominciato = false;
		this.utenti = new ArrayList<>();
		this.startTime = null;
		this.messaggio = new ArrayList<>();
	}

	public int getVincitore() {
		return vincitore;
	}

	public void setVincitore(int vincitore) {
		this.vincitore = vincitore;
	}

	public boolean isVendita() {
		return vendita;
	}

	public void setVendita(boolean vendita) {
		this.vendita = vendita;
	}

	public boolean isGiocoCominciato() {
		return giocoCominciato;
	}

	public void setGiocoCominciato(boolean giocoCominciato) {
		this.giocoCominciato = giocoCominciato;
	}

	public List<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}
	
	public void initGioco() {
		Random r = new Random();
		this.giocoCominciato = true;
		this.startTime = Instant.now();
		
		List<Integer> nums = new ArrayList<>();
		while(nums.size()!=30) {
			int num = r.nextInt(30)+1;
			if(!nums.contains(num)) {
				nums.add(num);
			}
		}
		int count=0;
		for(Utente u: utenti) {
			for(int i=0; i<10;i++) {
				u.getCarte().add(nums.get(count));
				count++;
			}
		}
	}
	
	public void passaggio(Utente venditore,Utente compratore) {
		int cartaVenditore = venditore.getCartaVendita();
		int denariVenditore = venditore.getDenari();
		int offertaCompratore = compratore.getOfferta();
		
		venditore.setCartaVendita(-1);
		compratore.getCarte().add(cartaVenditore);
		venditore.setDenari(denariVenditore+offertaCompratore);
		compratore.setDenari(compratore.getDenari()-offertaCompratore);
		compratore.setOfferta(-1);
		for(Utente u: this.utenti) {
			u.setOfferta(-1);
			u.setOffertaTime(null);
		}
		this.setVendita(false);
	}
	
	public Instant ultimaOffertaTime() {
		Instant ris=null;
		for(Utente u: this.utenti) {
			if(u.getOffertaTime()!=null && ris==null) {
				ris = u.getOffertaTime();
			}
			else if(u.getOffertaTime()!=null &&ris.isBefore(u.getOffertaTime())) {
				ris=u.getOffertaTime();
			}
		}
		return ris;
	}
	
	public Utente utenteVincitoreOfferta(){
		Utente ris = null;
		for(Utente u: this.utenti) {
			if(ris==null) {
				ris = u;
			}
			else if(u.getOfferta()>ris.getOfferta()) {
				ris = u;
			}
		}
		return ris;
	}
	
	public Utente utenteVenditore(){
		Utente ris = null;
		for(Utente u: this.utenti) {
			if(u.getCartaVendita()!=-1) {
				ris = u;
			}
		}
		return ris;
	}

	public List<String> getMessaggio() {
		return messaggio;
	}
	
	public void addMessaggio(String mess) {
		this.messaggio.add(mess);
		
	}

}
