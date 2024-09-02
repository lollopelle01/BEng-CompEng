/**
 * Interfaccia remota di servizio
 */

 import java.rmi.Remote;
 import java.rmi.RemoteException;
 
 public interface RMI_InterfaceFile extends Remote {
 
     int conta_numero_linee(String fileName, String parola) throws RemoteException;
 
     String[] lista_nomi_file_contenenti_parola_in_linea(String  direttorio, String parolaa) throws RemoteException;
 
 }