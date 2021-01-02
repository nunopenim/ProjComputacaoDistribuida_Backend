package maven.projetoCD;

/* How stuff works ID-wise:
 * ID = 0 			  : Gerador de IDs
 * ID > 0 			  : Objetos de votação
 * ID < 0 && ID > -10 : Parametros gerais (Tempo de inicio da votaçao, etc...
 * ID <= -10		  : Utilizadores que já votaram
 */

public class DBBackEnd {
	static IDGen dbcounter;
	static DBManager manager; //"votacoes"
	
	private boolean saveCounter() { //autosave to DB
		return manager.updateItem(dbcounter);
	}
	
	// Public stuff, the API per se
	
	public void startDB() {
		manager = new DBManager("votacoes");
		manager.connect();
		boolean present = manager.nameExists();
		manager.initConnection();
		if (!present) {
			dbcounter = new IDGen(0, -10);
			manager.addItem(dbcounter);
		}
		else {
			dbcounter = (IDGen) manager.findObj(IDGen.class, "0");
		}
	}
	
	public void addItemToVoteDB(String name) {
		ItemVotacao toAdd = new ItemVotacao(dbcounter.idGenerator(), name);
		saveCounter();
		manager.addItem(toAdd);
	}
	
	public boolean removeItemFromVoteDB(String id) {
		ItemVotacao toRemove = (ItemVotacao) manager.findObj(ItemVotacao.class, id);
		if (toRemove != null) {
			manager.removeItem(toRemove);
			return true;
		}
		return false;
	}
	
	public boolean voteInItem(String id) {
		ItemVotacao item = (ItemVotacao) manager.findObj(ItemVotacao.class, id);
		if (item == null) {
			return false;
		}
		item.vote();
		return manager.updateItem(item);
	}
	
}
