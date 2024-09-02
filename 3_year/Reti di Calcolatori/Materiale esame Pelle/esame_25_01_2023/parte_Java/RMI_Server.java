// pellegrino lorenzo 0000971455
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_interfaceFile {

	static int N = 7;
	static Riga[] tabella = new Riga[N];

	private boolean isPrecedente(int gg1, int mm1, int aaaa1, int gg2, int mm2, int aaaa2){ // vogliamo sapere se 2 >= 1
		if(aaaa2>aaaa1){
			return true;
		}
		else if(aaaa2<aaaa1){
			return false;
		}
		else{
			if(mm2>mm1){
				return true;
			}
			else if(mm2<mm1){
				return false;
			}
			else{
				if(aaaa2>aaaa1){
					return true;
				}
				else if(aaaa2<aaaa1){
					return false;
				}
				else{ // setesso giorno
					return true;
				}
			}
		}
	}

	// Costruttore
	public RMI_Server() throws RemoteException {
		super();
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

		// Inizializzazione della struttura dati richiesta
		for (int i=0; i<N; i++){ 
			tabella[i] = new Riga("L", "L", "L", -1, -1, -1);
			tabella[i].stampa();
		}

		// Inizializzazione della struttura dati per debug
		tabella[2] = new Riga("AN745NL", "brand1", "AN745NL_img", 20, 2, 2023);
		tabella[4] = new Riga("NU547PL", "brand1", "NU547PL_img", 22, 1, 2023);

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			RMI_Server serverRMI = new RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public int elimina_prenotazioni(int gg, int mm, int aaaa) throws RemoteException {
		int result = 0, i;
		File folder;
		try{
			for(i=0; i<N; i++){
				if(this.isPrecedente(gg, mm, aaaa, tabella[i].getGg(), tabella[i].getMm(), tabella[i].getAaaa())){
					folder = new File(tabella[i].getFolder());
					for(File entry : folder.listFiles()){
						entry.delete();
					}
					tabella[i] = new Riga("L", "L", "L", -1, -1, -1);
					result++;
				}
			}
		}catch(Exception e){result = -1;}
		return result;
	}

	@Override
	public String[] visualizza_prenotazioni(int gg, int mm, int aaaa) throws RemoteException {
		String result[] = new String[6];
		int i, count = 0;
		for(i=0; i<N && count<6; i++){
			if(this.isPrecedente(gg, mm, aaaa, tabella[i].getGg(), tabella[i].getMm(), tabella[i].getAaaa())){
				result[count] = tabella[i].getId() + "\t" + tabella[i].getGg()+"/"+tabella[i].getMm()+"/"+tabella[i].getAaaa() + "\t" + tabella[i].getBrand();
				count++;
			}
			else{
				result[count] = "";
				count++;
			}
		}
		return result;
	}

}