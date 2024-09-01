// pellegrino lorenzo 0000971455
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_interfaceFile extends Remote {

	public String[] lista_nomi_file_contenenti_parola_in_linea(String dirName, String parola) throws RemoteException;

	public int conta_numero_linee(String fileName, String parola) throws RemoteException;
}