// pellegrino lorenzo 0000971455
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_interfaceFile extends Remote {

	public int elimina_occorrenze(String fileName) throws RemoteException;

	public String[] lista_filetesto(String dirName) throws RemoteException;
}