/**
 * Interfaccia remota di servizio
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_InterfaceFile extends Remote {

	int elimina_prenotazione(String id) throws RemoteException;

	Prenotazioni[] visualizza_prenotazione(int num_persone, String tipo) throws RemoteException;

}

