import java.rmi.Remote;
import java.rmi.RemoteException;

public interface pellegrino_lorenzo_0000971455_RMI_InterfaceFile extends Remote{
    String[] lista_file(String dirName) throws RemoteException;
    int numerazione_righe(String fileName) throws RemoteException;
}