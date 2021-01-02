package maven.projetoCD;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Declaração de funções para o interface RMI

public interface RMI_Interface extends Remote{
	String testFunc() throws RemoteException;
}
