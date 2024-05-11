// pellegrino lorenzo 0000971455 prova2
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface pellegrino_lorenzo_0000971455_RMI_interfaceFile extends Remote {

	public int elimina_occorrenze (String fileName) throws RemoteException;

	public String[] lista_filetesto (String dirName) throws RemoteException;
}
