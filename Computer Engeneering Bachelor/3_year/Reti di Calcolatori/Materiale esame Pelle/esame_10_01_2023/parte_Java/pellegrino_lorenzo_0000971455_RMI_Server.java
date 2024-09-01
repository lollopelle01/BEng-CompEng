// pellegrino lorenzo 0000971455 prova2
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class pellegrino_lorenzo_0000971455_RMI_Server extends UnicastRemoteObject implements pellegrino_lorenzo_0000971455_RMI_interfaceFile {

	// Costruttore
	public pellegrino_lorenzo_0000971455_RMI_Server() throws RemoteException {
		super();
	}

	@Override
    public int elimina_occorrenze(String fileName) throws RemoteException {
        int result = 0, c;
        File oldFile = new File(fileName), newFile = new File("temp.txt");
        try {
            FileInputStream fis= new FileInputStream(oldFile);
            FileOutputStream fos = new FileOutputStream(newFile);
            while((c = fis.read()) > 0){
                if(c >= 97 && c<=122){ // carattere minuscolo da 'a' a 'z'
                    result++;
                }
                else{
                    fos.write(c);
                }
            }
            fis.close();
            fos.close();

            oldFile.delete();
            newFile.renameTo(oldFile);

        } catch (Exception e) {
           result = -1;
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String[] lista_filetesto(String dirName) throws RemoteException {
        File dir = new File(dirName);
        String[] result = null;
        int numFile = 0;

        if(!dir.isDirectory()){throw new RemoteException();}

        for(File entry : dir.listFiles()){
            if(numFile <= 6 && entry.isFile() && entry.getName().endsWith(".txt")){
                numFile++;
            }
        }
        result = new String[numFile];
        numFile = 0;
        for(File entry : dir.listFiles()){
            if(numFile <= 6 && entry.isFile() && entry.getName().endsWith(".txt")){
                result[numFile] = entry.getName();
                numFile++;
            }
        }
        return result;
    }


	public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "Server";

		// Controllo parametri
		if (args.length != 0 && args.length != 1) {
			System.out.println("Sintassi: Server [registryPort]");
			System.exit(1);
		}
		if (args.length == 1) {
			try {
				registryPort = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Sintassi: Server [registryPort], registryPort intero");
				System.exit(2);
			}
		}

		// Registrazione del servizio RMI
		String completeName = "//" + registryHost + ":" + registryPort + "/" + serviceName;
		try {
			pellegrino_lorenzo_0000971455_RMI_Server serverRMI = new pellegrino_lorenzo_0000971455_RMI_Server();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
