package maven.projetoCD;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Declaração de funções para o interface RMI

public interface RMI_Interface extends Remote{
	String testFunc() throws RemoteException;
	boolean[] authenticator(String uid, String pw) throws RemoteException;
	String listarItensEmVotacao() throws RemoteException;
	int getTotalDeVotos() throws RemoteException;
	boolean votar(String uid, String item_id) throws RemoteException;
	String obterItem(String id) throws RemoteException;
	boolean jaVotou(String uid) throws RemoteException;
	boolean addUserToVoters(String ldapID) throws RemoteException;
	String listarResultados() throws RemoteException;
	boolean hasStarted() throws RemoteException;
	boolean hasEnded() throws RemoteException;
	long startTime() throws RemoteException;
	long sessionLength() throws RemoteException;
	boolean setStarting(long value) throws RemoteException;
	boolean setLength(long value) throws RemoteException;
	String winningItem() throws RemoteException;
	boolean removeUserFromVoters(String ldapID) throws RemoteException;
	String getAllSessionVoters() throws RemoteException;
	String getAllVoters() throws RemoteException;
}
