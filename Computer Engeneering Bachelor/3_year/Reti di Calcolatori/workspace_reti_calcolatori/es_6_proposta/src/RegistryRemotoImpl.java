
/**
 * 	Implementazione del Registry Remoto.
 *	Metodi descritti nelle interfacce.  
 */

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegistryRemotoImpl extends UnicastRemoteObject implements
		RegistryRemotoServer, RegistryRemotoClient {

	// num. entry [nomelogico][ref]
	final int tableSize = 100;
	final String[] tags= {"tag1", "tag2", "tag3", "tag4", "tag5"}; 

	// Tabella: la prima colonna contiene i nomi, la seconda i riferimenti remoti, la terza i tag
	private Object[][] table = new Object[tableSize][2 + tags.length]; // aggiungo una colonna a tag + 2 colonne(1 per nomi e una per riferimenti)

	public RegistryRemotoImpl() throws RemoteException {
		super();
		for (int i = 0; i < tableSize; i++) {
			table[i][0] = null;
			table[i][1] = null;
			
			for(int j=0; j<tags.length; j++) {
				table[i][2 + j]=""; //inizializzo tag a vuoti
			}
		}
	}
	
	@Override
	public String[] cercaNome(String Tag) throws RemoteException {
		String[] result = new String[tableSize];
		int k = 0;
		for(int i=0; i<tags.length; i++) {
			if(tags[i].compareTo(Tag)==0) {
				for(int j=0; j<tableSize; j++) {
					if(((String) table[j][2+i]).compareTo(Tag)==0) {
						result[k]=(String) table[j][2+i];
						k++;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean associaTag(String nomeLogico, String tag) throws RemoteException {
		for(int i = 0; i<tableSize; i++) {
			if( ((String) table[i][0]).compareTo(nomeLogico)==0 ) { //se c'è l'elemento
				for(int j = 0; i<table[i].length; j++) {
					if(tags[j].compareTo(tag)==0) {
						if(((String) table[i][2+j]).compareTo(tag)==0)
							return false;
						if(((String) table[i][2+j]).compareTo("")==0) {
							table[i][2+j]=tag;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/** Aggiunge la coppia nella prima posizione disponibile */
	public synchronized boolean aggiungi(String nomeLogico, Remote riferimento)
			throws RemoteException {
		// Cerco la prima posizione libera e la riempio
		boolean risultato = false;
		if ((nomeLogico == null) || (riferimento == null))
			return risultato;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] == null) {
				table[i][0] = nomeLogico;
				table[i][1] = riferimento;
				risultato = true;
				break;
			}
		return risultato;
	}

	/** Restituisce il riferimento remoto cercato, oppure null */
	public synchronized Remote cerca(String nomeLogico) throws RemoteException {
		Remote risultato = null;
		if (nomeLogico == null)
			return null;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null && nomeLogico.equals((String) table[i][0])) {
				risultato = (Remote) table[i][1];
				break;
			}
		return risultato;
	}

	/** Restituisce tutti i riferimenti corrispondenti ad un nome logico */
	public synchronized Remote[] cercaTutti(String nomeLogico)
			throws RemoteException {
		int cont = 0;
		if (nomeLogico == null)
			return new Remote[0];
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null && nomeLogico.equals((String) table[i][0]))
				cont++;
		Remote[] risultato = new Remote[cont];
		// Ora lo uso come indice per il riempimento
		cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null && nomeLogico.equals((String) table[i][0]))
				risultato[cont++] = (Remote) table[i][1];
		return risultato;
	}

	/** Restituisce tutti i riferimenti corrispondenti ad un nome logico */
	public synchronized Object[][] restituisciTutti() throws RemoteException {
		int cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null)
				cont++;
		Object[][] risultato = new Object[cont][2];
		// Ora lo uso come indice per il riempimento
		cont = 0;
		for (int i = 0; i < tableSize; i++)
			if (table[i][0] != null) {
				risultato[cont][0] = table[i][0];
				risultato[cont][1] = table[i][1];
			}
		return risultato;
	}

	/** Elimina la prima entry corrispondente al nome logico indicato */
	public synchronized boolean eliminaPrimo(String nomeLogico)
			throws RemoteException {
		boolean risultato = false;
		if (nomeLogico == null)
			return risultato;
		for (int i = 0; i < tableSize; i++)
			if (nomeLogico.equals((String) table[i][0])) {
				table[i][0] = null;
				table[i][1] = null;
				risultato = true;
				break;
			}
		return risultato;
	}

	public synchronized boolean eliminaTutti(String nomeLogico)
			throws RemoteException {
		boolean risultato = false;
		if (nomeLogico == null)
			return risultato;
		for (int i = 0; i < tableSize; i++)
			if (nomeLogico.equals((String) table[i][0])) {
				if (risultato == false)
					risultato = true;
				table[i][0] = null;
				table[i][1] = null;
			}
		return risultato;
	}

	// Avvio del Server RMI
	public static void main(String[] args) {

		int registryRemotoPort = 1099;
		String registryRemotoHost = "localhost";
		String registryRemotoName = "RegistryRemoto";

		// Controllo dei parametri della riga di comando
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: ServerImpl [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) {
			try {
				registryRemotoPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out
						.println("Sintassi: ServerImpl [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Impostazione del SecurityManager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());

		// Registrazione del servizio RMI
		String completeName = "//" + registryRemotoHost + ":" + registryRemotoPort
				+ "/" + registryRemotoName;
		try {
			RegistryRemotoImpl serverRMI = new RegistryRemotoImpl();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + registryRemotoName
					+ "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + registryRemotoName + "\": "
					+ e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}