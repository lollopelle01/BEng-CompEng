import java.rmi.Remote;
import java.rmi.RemoteException;

public interface pellegrino_lorenzo_0000971455_RMI_interfaceFile extends Remote {

    int elimina_prenotazione(String numTarga) throws RemoteException;
    String[] visualizza_prenotazioni(String tipoVeicolo) throws RemoteException;
    
}