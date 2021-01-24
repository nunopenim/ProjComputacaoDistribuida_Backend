package maven.projetoCD;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main implements RMI_Interface{
	
	// Internal use only
	
	private boolean voteWrapper(String uid) {
		return DBBackEnd.updateVoteStatus(uid);
	}
	
	private long currentTimeSeconds() {
		return Instant.now().getEpochSecond();
	}
	
	// RMI-able funcs
	
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

	public int getTotalDeVotos() {
		return DBBackEnd.totalVotos();
	}
	
	public String listarItensEmVotacao() {
		ArrayList<String> votingItems = DBBackEnd.getAllVotingItems();
		String retString = "";
		for (String s : votingItems) {
			String[] components = s.split(Pattern.quote("|"));
			retString += "(" + components[0] + ") " + components[1] + "|";
		}
		return retString;
	}
	
	public String obterItem(String id) {
		return DBBackEnd.getVotingItem(id);
	}
	
	public boolean jaVotou(String uid) {
		return DBBackEnd.hasVoted(uid);
	}
	
	public boolean votar(String uid, String item_id) {
		if (!voteWrapper(uid)) { //user nao existe ou falha a mudar para true
			return false;
		}
		if (DBBackEnd.canThisVote(uid)) {
			return DBBackEnd.voteInItem(item_id);
		}
		return false;
	}
	
	public boolean addUserToVoters(String ldapID) {
		if (DBBackEnd.userExists(ldapID)) {
			return false;
		}
		try {
			DBBackEnd.addUserToVoteDB(ldapID);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeUserFromVoters(String ldapID) {
		try {
			boolean a = DBBackEnd.removeUserFromVoteDB(ldapID);
			return a;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public String listarResultados() {
		String retString = "";
		int totalDeVotos = DBBackEnd.totalVotos();
		ArrayList<String> allItems = DBBackEnd.getAllVotingItems();
		for (String s : allItems) {
			String strToAdd = "";
			String[] parts = s.split(Pattern.quote("|"));
			strToAdd += "(" + parts[0] +") " + parts[1] + " - ";
			float percent = ((float)(Integer.parseInt(parts[2])/ (float) totalDeVotos)) * 100;
			strToAdd += String.format("%.02f", percent);
			retString += strToAdd + "%|";
		}
		return retString;
	}
	
	public boolean hasStarted() {
		if (DBBackEnd.getStartTime() <= currentTimeSeconds()) {
			return true;
		}
		return false;
	}
	
	public boolean hasEnded() {
		if (DBBackEnd.getStartTime() + DBBackEnd.getStopTime() <= currentTimeSeconds()) {
			return true;
		}
		return false;
	}
	
	public long startTime() {
		return DBBackEnd.getStartTime();
	}
	
	public long sessionLength() {
		return DBBackEnd.getStopTime();
	}
	
	public boolean setStarting(long value) {
		return DBBackEnd.setStartTime(value);
	}
	
	public boolean setLength(long value) {
		return DBBackEnd.setStopTime(value);
	}
	
	public String getAllSessionVoters() {
		ArrayList<Votante> voters = DBBackEnd.getAllVoters();
		String retS = "";
		for (Votante v : voters) {
			if(v.hasVoted()) {
				LoginSession martelada = AuthManager.createSession(v.getLdapId(), "0", false);
				String name = AuthManager.getName(martelada);
				String params = v.getLdapId()+ " " + name + "|";
				retS += params;
			}
		}
		return retS;
	}
	
	public String getAllVoters() {
		ArrayList<Votante> voters = DBBackEnd.getAllVoters();
		String retS = "";
		for (Votante v : voters) {
			LoginSession martelada = AuthManager.createSession(v.getLdapId(), "0", false);
			String name = AuthManager.getName(martelada);
			String params = v.getLdapId()+ " " + name + "|";
			retS += params;
		}
		return retS;
	}
	
	public String winningItem() {
		String item = DBBackEnd.winningItem();
		String retString = "";
		String[] components = item.split(Pattern.quote("|"));
		retString += "(" + components[0] + ") " + components[1];  
		return retString;
	}
	
	public static void main(String args[]) {
		DBBackEnd.startDB();
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
