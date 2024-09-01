import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class pellegrino_lorenzo_0000971455_RMI_Server extends UnicastRemoteObject implements pellegrino_lorenzo_0000971455_RMI_InterfaceFile{
    
    // Costruttore
    public pellegrino_lorenzo_0000971455_RMI_Server() throws RemoteException{
        super();
    }

    private boolean isVocale(char c){
        if(c=='a' || c=='A' || c=='e' || c=='E' || c=='i' || c=='I' || c=='o' || c=='O' || c=='u' || c=='U'){
            return true;
        }
        else return false;
    }

    public String[] lista_file(String dirName) throws RemoteException{
        String[] result = null;
        int result_lenght = 0, count = 0;
        File folder = new File(dirName);
        char prec, act;

        for(File entry : folder.listFiles()){ //prima devo sapere quanto potrà essere grande l'array
        System.out.print("Ho beccato: " + entry.getName());
            for(int i=1; i<entry.getName().length(); i++){
                act = entry.getName().charAt(i);
                prec = entry.getName().charAt(i-1);
                if(isVocale(act) && isVocale(prec)){
                    if(entry.isFile()){
                        result_lenght++;
                        System.out.println(" --> è un file");
                    }
                    if(entry.isDirectory()){
                        System.out.println("--> è una directory");
                        for(File entry2 : entry.listFiles()){ //prima devo sapere quanto potrà essere grande l'array
                            System.out.print("\tHo beccato: " + entry2.getName());
                            for(int j=1; j<entry2.getName().length(); j++){
                                act = entry2.getName().charAt(j);
                                prec = entry2.getName().charAt(j-1);
                                if(isVocale(act) && isVocale(prec)){
                                    if(entry2.isFile()){
                                        result_lenght++; System.out.println(" --> è un file");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        result = new String[result_lenght];

        for(File entry : folder.listFiles()){ //prima devo sapere quanto potrà essere grande l'array
            for(int i=1; i<entry.getName().length(); i++){
                act = entry.getName().charAt(i);
                prec = entry.getName().charAt(i-1);
                if(isVocale(act) && isVocale(prec)){
                    if(entry.isFile()){
                        result[count] = entry.getName();
                        count++;
                    }
                    if(entry.isDirectory()){
                        for(File entry2 : entry.listFiles()){ //prima devo sapere quanto potrà essere grande l'array
                            for(int j=1; j<entry2.getName().length(); j++){
                                act = entry2.getName().charAt(j);
                                prec = entry2.getName().charAt(j-1);
                                if(isVocale(act) && isVocale(prec)){
                                    if(entry2.isFile()){
                                        result[count] = entry2.getName();
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public int numerazione_righe(String fileName) throws RemoteException{
        int result = 1;
        try{
            File file = new File(fileName), fileTemp = new File("temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileTemp));
            String lineIn, lineOut;

            while((lineIn=br.readLine())!=null){
                lineOut = result + ") " + lineIn + "\n";
                bw.write(lineOut);
                result++;
            }
            bw.close();
            br.close();

            file.delete();
            fileTemp.renameTo(file);
        }catch(Exception e){
            throw new RemoteException("Errore in lettura");
        }
        return result;
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