// pellegrino lorenzo 0000971455
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server extends UnicastRemoteObject implements RMI_interfaceFile {

	// Costruttore
	public RMI_Server() throws RemoteException {
		super();
	}
	public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: ServerImpl [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) {
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: ServerImpl [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			RMI_Server serverRMI = new RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public synchronized int elimina_occorrenze(String fileName) throws RemoteException {
		int result, c;
		File file, dest;
		FileReader fr;
		FileWriter fw;
		char carattere;
		try{
			file = new File(fileName);
			if(file.isFile() && file.getName().endsWith(".txt")){
				dest = new File(fileName+"_temp");
				fr=new FileReader(file);
				fw=new FileWriter(dest);
				result = 0;
				while((c=fr.read())>=0){
					carattere = (char) c;
					if(carattere>='a' && carattere<='z'){
						result++;
					}
					else{
						fw.write(c);
					}
				}
				fr.close();
				fw.close();

				file.delete();
				dest.renameTo(file);
			}
			else{
				result = -1;
			}
		}catch(Exception e){
			result = -1;
		}

		return result;
	}
	@Override
	public String[] lista_filetesto(String dirName) throws RemoteException {
		String[] result = new String[6];
		File dir;
		int dim=0;
		try{
			dir = new File(dirName);
			if(dir.isDirectory()){
				for(File entry : dir.listFiles()){
					if(dim<6 && entry.isFile() && entry.getName().endsWith(".txt")){
						result[dim]=entry.getName();
						dim++;
					}
				}
			}
			else{
				throw new Exception("1");
			}

		}catch(Exception e){
			throw new RemoteException("1");
		}

		return result;
	}

}