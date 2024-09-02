// pellegrino lorenzo 0000971455 compito 1
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_interfaceFile extends Remote {

	public int elimina_prenotazioni(int gg, int mm, int aaaa) throws RemoteException;

	public String[] visualizza_prenotazioni(int gg, int mm, int aaaa) throws RemoteException;

}