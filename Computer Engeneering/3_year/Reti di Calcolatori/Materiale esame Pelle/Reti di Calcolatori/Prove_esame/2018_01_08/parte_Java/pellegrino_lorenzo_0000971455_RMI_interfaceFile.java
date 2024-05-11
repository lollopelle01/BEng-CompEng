import java.rmi.Remote;
import java.rmi.RemoteException;

public interface pellegrino_lorenzo_0000971455_RMI_interfaceFile extends Remote {
	public String[] visualizza_lista () throws RemoteException; //Array di città
    public int visualizza_numero (String citta, String via) throws RemoteException; // num di calze in (citta, via)
                                                                                    // -1 altrimenti
}