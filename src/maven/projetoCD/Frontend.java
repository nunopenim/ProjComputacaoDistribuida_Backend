package maven.projetoCD;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// TomCat frontend

public class Frontend {
	private Frontend() {
	}
	
	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            RMI_Interface stub = (RMI_Interface) registry.lookup("test_connection");
            String response = stub.testFunc();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
	}
	
}
