import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements RemOp {
	private File oldFile, newFile;

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public Esito elimina_riga(String nome_file_remoto, int intero) throws RemoteException { //intero è la riga da eliminare
		Esito result = null;
		try {
			oldFile = new File(nome_file_remoto);
			if(!oldFile.exists()) {throw new RemoteException();}
			if(!oldFile.getAbsolutePath().endsWith(".txt")) {throw new RemoteException();}
			BufferedReader br = new BufferedReader(new FileReader(oldFile));
			if(br.lines().toArray().length < intero) {br.close(); new RemoteException();}
			else {
				br.close();
				br = new BufferedReader(new FileReader(oldFile));
				newFile = new File(oldFile.getName().substring(0, oldFile.getName().length() - 4) + "_SenzaRiga" + intero + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
				int riga = 0;
				String linea;
				while((linea=br.readLine())!=null) {
					riga++;
					if(riga != intero) {bw.write(linea + "\n");}
				}
				br.close();
				bw.close();
				result = new Esito(newFile.getAbsolutePath(), riga-1);
			}
		}catch(Exception e) {throw new RemoteException();}
		return result;
	}

	@Override
	public int conta_righe(String nome_file_remoto, int intero) throws RemoteException {
		int result = 0;
		try{
			oldFile = new File(nome_file_remoto);
			BufferedReader br = new BufferedReader(new FileReader(oldFile));
			String linea;
			String[] parole;
			while((linea=br.readLine())!=null){
				parole = linea.split(" ");
				if(parole.length >= intero) {
					result++;
				}
			}
			br.close();
		}
		catch(Exception e) { throw new RemoteException();}
		return result;
	}
	
	// Avvio del Server RMI (da controllare)
	public static void main(String[] args) {

		int registryPort = 1099;
		String registryHost = "localhost";
		String serviceName = "RemOp";

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
			ServerImpl serverRMI = new ServerImpl();
			Naming.rebind(completeName, serverRMI);
			System.out.println("Server RMI: Servizio \"" + serviceName + "\" registrato");
		} catch (Exception e) {
			System.err.println("Server RMI \"" + serviceName + "\": " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
