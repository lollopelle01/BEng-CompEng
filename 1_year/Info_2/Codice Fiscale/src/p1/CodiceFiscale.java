package p1;

public class CodiceFiscale {
	
	private Boolean isConsonante(char c) {
		String s1="AEIOUaeiou";
		if(s1.indexOf(c)>=0 && s1.indexOf(c)<10)
			return false;
		else
			return true;
	}
	private String calcolaCognome(String cognomee) {
		String result="";
		String cognome="";
		int counter=0, counter1=0;
		char ch;
		int cons=countCons(cognome);
		int voc=cognome.length()-cons;
		
		//eliminare gli spazi con la funzione replace
		cognome=cognome+cognomee.toUpperCase();
		cons=countCons(cognome);
		voc=cognome.length()-cons;
		
		if(cons>=3) {
			for(int i=0; i<cognome.length() && counter<3; i++) {
				if(isConsonante(cognome.charAt(i))) {
					if(cognome.charAt(i)>=97 && cognome.charAt(i)<=122) {
						ch=(char) (cognome.charAt(i)-32);
					}
					else
						ch=cognome.charAt(i);
					result=result+ch;
					counter++;
				}
			}
		}
		else if(cons<3 && cons+voc>=3){
			for(int i=0; i<cognome.length(); i++) {
				if(isConsonante(cognome.charAt(i))) {
					result=result+cognome.charAt(i);
					counter++;
				}
			}
			for(int i=0; i<cognome.length() && counter1<3-counter; i++) {
				if(!isConsonante(cognome.charAt(i))) {
					result=result+cognome.charAt(i);
					counter1++;
				}
			}
		}
		else {
			for(int i=0; i<cognome.length(); i++) {
				if(isConsonante(cognome.charAt(i))) {
					result=result+cognome.charAt(i);
					counter++;
				}
			}
			for(int i=0; i<cognome.length() && counter1<3-counter; i++) {
				if(!isConsonante(cognome.charAt(i))) {
					result=result+cognome.charAt(i);
					counter1++;
				}
			}
			for(int i=0; i<3-counter1; i++) {
				result=result+"X";
			}
		}
		return result;
	}
	private int countCons(String s) {
		int result=0;
		for(int i=0; i<s.length(); i++) {
			if(isConsonante(s.charAt(i))) {
				result++;
			}
		}
		return result;
	}
	private String calcolaNome(String nome) {
		String result="", nuovo="";
		int cons=countCons(nome);
		int voc=nome.length()-cons;
		int counter=0;
		int counter1=0;
		char ch;
		//eliminare gli spazi con la funzione replace
		for(int i=0; i<nome.length();i++) {
			ch=nome.charAt(i);
			if(ch>=97 && ch<=122) {
				ch=(char) (ch-32);
			}
			nuovo=nuovo+ch;
		}
		if(cons>=4) {
			for(int i=0; i<nuovo.length() && counter<=3; i++) {
				if(isConsonante(nuovo.charAt(i))) {
					if(counter!=1) {
						result=result+nuovo.charAt(i);
					}
					counter++;
				}
			}
		}
		else if(cons==3) {
			for(int i=0; i<nuovo.length() && counter<3; i++) {
				if(isConsonante(nuovo.charAt(i))) {
					result=result+nuovo.charAt(i);
					counter++;
				}
			}
		}
		else if(cons<3 && cons+voc>=3) {
			for(int i=0; i<nuovo.length(); i++) {
				if(isConsonante(nuovo.charAt(i))) {
					result=result+nuovo.charAt(i);
					counter++;
				}
			}
			for(int i=0; i<nuovo.length() && counter1<3-counter; i++) {
				if(!isConsonante(nuovo.charAt(i))) {
					result=result+nuovo.charAt(i);
					counter1++;
				}
			}
		}
		else {
			for(int i=0; i<nuovo.length(); i++) {
				if(isConsonante(nuovo.charAt(i))) {
					result=result+nuovo.charAt(i);
					counter++;
				}
			}
			for(int i=0; i<nuovo.length() && counter1<3-counter; i++) {
				if(!isConsonante(nuovo.charAt(i))) {
					result=result+nuovo.charAt(i);
					counter1++;
				}
			}
			for(int i=0; i<3-counter1; i++) {
					result=result+"X";
			}
		}
		return result;
	}
	private String calcolaAnno(int anno) {
		String result="";
		String cifra;
		cifra=String.valueOf(anno).toString();
		result=cifra.substring(cifra.length()-2); //la funzione dà la sottostringa da 4-2=2 ovvero 01 su 2001 ad esempio
		return result;
	}
	private char calcolaMese(int mese) {
		char result;
		String caratteri="ABCDEHLMPRST";
		result=caratteri.charAt(mese-1);
		return result;
	}
	private String calcolaGiornoSesso(int giorno, char sesso){
		String result="";
		if(sesso=='M' && giorno>=1 && giorno<=31) {
			if(giorno<10) {
				result=result+"0";
			}
			result=result+String.valueOf(giorno).toString();
		}
		if(sesso=='F' && giorno>=1 && giorno<=31) {
			result=String.valueOf(giorno+40).toString();
		}
		return result;
	}
	private String calcolaComune(String comune) {
		String result="";
		//dopo aver fatto la lettura da file, bisogna fare la lettura del codice in base al nome e quindi per ora
		//è richiesto direttamente il codice del comune (comune)
		result=result+comune;
		return result;
	}
	private char calcolaCarControllo(String codice) {
		char result, ch;
		int tot=0;
		int resto=0;
		int valore=0;
		String caratteri="BAKPLCQDREVOSFTGUHMINJWZYX";
		String nuovo="";
		for(int i=0; i<codice.length(); i++) {
			ch=codice.charAt(i);
			if(ch<=57 && ch>=48) {
				ch=(char) (ch+17);
			}
				nuovo=nuovo+ch;
		}
		for(int i=0; i<nuovo.length(); i++) {
			if(i%2!=0) {
				valore=((int) nuovo.charAt(i))-65;
			}
			else{
				valore=caratteri.indexOf(nuovo.charAt(i));
			}
			tot=tot+valore;
		}
		resto=tot%26;
		result=(char) (resto+65);
		return result;
	}
	
	public String calcolaCodiceFiscale(String cognome, String nome, int giorno, int mese, int anno, char sesso, String comune) {
		String result="";
		result=calcolaCognome(cognome)+calcolaNome(nome)+calcolaAnno(anno)+calcolaMese(mese)+calcolaGiornoSesso(giorno, sesso)+calcolaComune(comune);
		result=result+calcolaCarControllo(result);
		return result;
	}
	public Boolean verificaCodiceFiscale(String cognome, String nome, int giorno, int mese, int anno, char sesso, String comune, String codice) {
		String temp="";
		temp=calcolaCognome(cognome)+calcolaNome(nome)+calcolaAnno(anno)+calcolaMese(mese)+calcolaGiornoSesso(giorno, sesso)+calcolaComune(comune);
		temp=temp+calcolaCarControllo(temp);
		if(temp.compareTo(codice)==0)
			return true;
		else
			return false;
	}

	
}