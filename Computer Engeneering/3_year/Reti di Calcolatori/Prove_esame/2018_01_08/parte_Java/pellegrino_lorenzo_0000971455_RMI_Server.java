import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class pellegrino_lorenzo_0000971455_RMI_Server extends UnicastRemoteObject implements pellegrino_lorenzo_0000971455_RMI_interfaceFile{
    
    private int N = 10;
    private Object[][] tabella;

    private void stampa(){
        System.out.println("\nTabella calze:");
        for(int i=0; i<N; i++){
            System.out.println(i+"/------------------------------------------------------------------------/");
            System.out.println("ID: " + this.tabella[i][0]);
            System.out.println("Tipo: " + this.tabella[i][1]);
            System.out.println("Carbone: " + this.tabella[i][2]);
            System.out.println("Citta: " + this.tabella[i][3]);
            System.out.println("Via: " + this.tabella[i][4]);
            System.out.println("Messaggio: " + this.tabella[i][5]);
        }
    }
    
    public pellegrino_lorenzo_0000971455_RMI_Server() throws RemoteException{
        this.tabella = new Object[N][6];
        /* COME ANDREBBE FATTO
        for(int i=0; i<N; i++){
            for(int j=0; j<6; j++){
                if(j==2){
                    this.tabella[i][j]='N';
                }
                this.tabella[i][j]="N";
            }
        }
        */

        //COME LO FACCIO PER NON STARE AD INSERIRE SEMPRE I DATI
        String[] cities = {"Bologna", "Milano", "Roma", "Bologna", "Milano", "Roma", "Bologna", "Milano", "Roma", "Aosta"};
        String[] vie = {"via1", "via2", "via3", "via1", "via2", "via3", "via3", "via2", "via1", "via4"};
        for(int i=0; i<N; i++){
            for(int j=0; j<6; j++){
                if(j==2){ //campo Carbone
                    this.tabella[i][j]='L';
                }
                else if(j==3){ //campo Città
                    this.tabella[i][j]=cities[i];
                }
                else if(j==4){ //campo Città
                    this.tabella[i][j]=vie[i];
                }
                else this.tabella[i][j]="L";
            }
        }

        System.out.println("Inizializzazione completata");
        this.stampa();
        
    }

    private boolean giaPresente(String s, String[] lista){
        boolean trovata = false;
        for(int i=0; i<lista.length && !trovata; i++){
            if(lista[i]!=null && s.compareTo(lista[i])==0){
                trovata=true;
            }
        }
        return trovata;
    }

    @Override
    public String[] visualizza_lista() throws RemoteException {
        String[] result = new String[N];
        
        int indice = 3, j=0; //considero solo città
        result[0] = (String) this.tabella[0][indice];
        j++;
        for(int i=1; i<N; i++){
            if(!this.giaPresente((String)this.tabella[i][indice], result)){
                result[j] = (String) this.tabella[i][indice];
                j++;
            }
        }

        /*PER OTTIMIZZARE*/
        String[] resultOptimus = new String[j];
        for(int k=0; k<j; k++){
            resultOptimus[k] = result[k];
        }
        return resultOptimus;
    }

    @Override
    public int visualizza_numero(String citta, String via) throws RemoteException {
        int result=0;
        for(int i=0; i<N; i++){
            if(citta.compareTo((String) this.tabella[i][3])==0 && via.compareTo((String) this.tabella[i][4])==0){
                result++;
            }
        }
        if(result==0){result = -1;}
        return result;
    }

    public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: ServerImpl [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) {
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: ServerImpl [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			pellegrino_lorenzo_0000971455_RMI_Server serverRMI = new pellegrino_lorenzo_0000971455_RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

}