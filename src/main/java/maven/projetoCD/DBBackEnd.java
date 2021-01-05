package maven.projetoCD;

import java.util.ArrayList;

/* How stuff works ID-wise:
 * ID = 0 			  : Gerador de IDs, some parameters
 * ID > 0 			  : Objetos de votação
 * ID < 0		      : Utilizadores
 */

public class DBBackEnd {
	static IDGen dbcounter;
	static DBManager manager; //"votacoes"
	
	private static boolean saveCounter() { //autosave to DB
		return manager.updateItem(dbcounter);
	}
	
	// Public stuff, the API per se
	
	public static void startDB() {
		manager = new DBManager("votacoes");
		manager.connect();
		boolean present = manager.nameExists();
		manager.initConnection();
		if (!present) {
			dbcounter = new IDGen(0, 0, 0, 0, 0);
			manager.addItem(dbcounter);
		}
		else {
			dbcounter = (IDGen) manager.findObj(IDGen.class, "0");
		}
	}
	
	public static void addItemToVoteDB(String name) {
		ItemVotacao toAdd = new ItemVotacao(dbcounter.idGenerator(), name);
		manager.addItem(toAdd);
		saveCounter();
	}
	
	public static void addUserToVoteDB(String ldapID) {
		Votante toAdd = new Votante(dbcounter.userIDGenerator(), ldapID);
		manager.addItem(toAdd);
		saveCounter();
	}
	
	public static boolean removeItemFromVoteDB(String id) {
		ItemVotacao toRemove = (ItemVotacao) manager.findObj(ItemVotacao.class, id);
		if (toRemove != null) {
			manager.removeItem(toRemove);
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getAllVotingItems() {
		ArrayList<String> retList = new ArrayList <String>();
		for (int i=1; i <= dbcounter.counterValue; i++) {
			ItemVotacao v = (ItemVotacao) manager.findObj(ItemVotacao.class, "" + i);
			String vData = v.getId() + "|" + v.nome + "|" + v.contagem;
			retList.add(vData);
		}
		return retList;
	}
	
	public static ArrayList<Votante> getAllVoters() {
		ArrayList<Votante> retList = new ArrayList<Votante>();
		for (int i = -1; i >= dbcounter.userIDGen; i--) {
			Votante v = (Votante) manager.findObj(Votante.class, "" + i);
			retList.add(v);
		}
		return retList;
	}
	
	public static boolean canThisVote(String ldapID) {
		boolean retBool = false;
		ArrayList<Votante> voters = getAllVoters();
		for (Votante v : voters) {
			if (v.getLdapId().equals(ldapID)) {
				retBool = true;
			}
		}
		return retBool;
	}
	
	public static boolean hasVoted(String ldapID) {
		boolean retBool = false;
		ArrayList<Votante> voters = getAllVoters();
		for (Votante v : voters) {
			if (v.getLdapId().equals(ldapID) && v.hasVoted()) {
				retBool = true;
			}
		}
		return retBool;
	}
	
	public static boolean updateVoteStatus(String ldapID) {
		Votante j = null;
		ArrayList<Votante> voters = getAllVoters();
		for (Votante v : voters) {
			if (v.getLdapId().equals(ldapID)) {
				j = v;
			}
		}
		if (j == null) {
			return false;
		}
		j.votar();
		return manager.updateItem(j);
	}
	
	public static String getVotingItem(String id) {
		ItemVotacao v = (ItemVotacao) manager.findObj(ItemVotacao.class, id);
		return v.getId() + "|" + v.nome + "|" + v.contagem;
	}
	
	public static int totalVotos() {
		dbcounter = (IDGen) manager.findObj(IDGen.class, "0");
		return dbcounter.voteCount;
	}
	
	public static boolean voteInItem(String id) {
		ItemVotacao item = (ItemVotacao) manager.findObj(ItemVotacao.class, id);
		if (item == null) {
			return false;
		}
		dbcounter.incVoteCount();
		item.vote();
		saveCounter();
		return manager.updateItem(item);
	}
	
}
