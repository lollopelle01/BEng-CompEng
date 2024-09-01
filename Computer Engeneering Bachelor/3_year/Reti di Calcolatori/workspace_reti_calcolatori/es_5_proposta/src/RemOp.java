import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemOp extends Remote{
	Esito elimina_riga(String nome_file_remoto, int intero) throws RemoteException;
	
	int conta_righe(String nome_file_remoto, int intero) throws RemoteException;
}
