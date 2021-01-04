package maven.projetoCD;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Main implements RMI_Interface{
	
	DBBackEnd dbbackend = new DBBackEnd();
	
	public Main() {
		dbbackend = new DBBackEnd();
	}
	
	public String testFunc() {
		return "Backend Operational";
	}
	
	public boolean[] authenticator(String uid, String pw) {
		boolean[] response = new boolean[2];
		response[0] = false;
		response[1] = false;
		boolean is_admin = false;
		LoginSession user = AuthManager.createSession(uid, pw, false);
		boolean validSession = AuthManager.validSession(user);
		if (!validSession) {
			user = AuthManager.createSession(uid, pw, true);
			validSession = AuthManager.validSession(user);
			if (!validSession) {
				return response;
			}
			else {
				is_admin = true;
			}
		}
		
		// tentamos autenticar
		boolean authenticateMe = AuthManager.authenticate(user);
		if (!authenticateMe) {
			return response;
		}
		response[0] = true;
		response[1] = is_admin;
		AuthManager.disconnect();
		return response;
	}
	
	public static void main(String args[]) {
		//Listener
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
	        System.out.println("RMI registry ready.");
            Main obj = new Main();
            RMI_Interface stub = (RMI_Interface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("test_connection", stub);
            System.out.println("Ready!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
