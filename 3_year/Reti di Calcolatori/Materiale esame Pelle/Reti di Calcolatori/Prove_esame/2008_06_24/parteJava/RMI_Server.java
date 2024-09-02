// pellegrino lorenzo 0000971455
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_interfaceFile {

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
	public String[] lista_nomi_file_contenenti_parola_in_linea(String dirName, String parola) throws RemoteException {
		String[] result = null;
		int dimL = 0;

		try{

			for(File entry : new File(dirName).listFiles()){
				if(conta_numero_linee(entry.getAbsolutePath(), parola) > 0){
					dimL++;
				}
			}
	
			result = new String[dimL];
			dimL = 0;
	
			for(File entry : new File(dirName).listFiles()){
				if(conta_numero_linee(entry.getAbsolutePath(), parola) > 0){
					result[dimL] = entry.getName();
					dimL++;
				}
			}
	
		}catch(Exception e){}
		
		return result;
	}

	@Override
	public int conta_numero_linee(String fileName, String parola) throws RemoteException {
		int result = 0;
		boolean trovato = false;
		String buffParola = "";
		int c;
		char carattere;

		try{

			FileReader fr = new FileReader(fileName);
			while((c=fr.read()) > 0){
				carattere = (char) c;
				if(carattere==' ' || carattere=='\n'){ // fine parola
					if(buffParola.compareTo(parola)==0){trovato=true;}
					buffParola = "";
					if(carattere=='\n' && trovato){ // fine riga e parola c'è
						result++;
					}
					if(carattere=='\n'){ // fine riga e parola non c'è
						trovato=false;
					}
				}		
				else{ // è una parola
					buffParola += carattere;
				}
			}
			if(parola.compareTo("")!=0){ // non è finito con ' ' o '\n' ma con EOF
				if(buffParola.compareTo(parola)==0){trovato=true;}
				buffParola = "";
				if(trovato){ // fine riga e parola c'è
					result++;
				}
			}
			fr.close();

		}catch(Exception e){ result = -1;}

		if(result == 0){result = -1;}

		return result;
	}
}