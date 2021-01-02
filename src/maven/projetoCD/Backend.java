package maven.projetoCD;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Backend implements RMI_Interface{
	public Backend() {
	}
	
	public String testFunc() {
		return "Backend Operational";
	}
	
	public static void main(String args[]) {
		AuthManager auther = new AuthManager();
		DBBackEnd dbbackend = new DBBackEnd();
		
		//Listener
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
	        System.out.println("RMI registry ready.");
            Backend obj = new Backend();
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
