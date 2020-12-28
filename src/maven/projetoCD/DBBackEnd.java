package maven.projetoCD;

import java.util.List;

public class DBBackEnd {
	static int counter;
	static DBManager manager; //"votacoes"
	
	public static void startDB() {
		boolean present = manager.nameExists();
		manager.initConnection();
		if (!present) {
			counter = 0;
			IDGen dbcounter = new IDGen(counter);
			manager.addItem(dbcounter);
		}
		else {
			IDGen dbcounter = (IDGen) manager.findObj(IDGen.class, "0");
			counter = dbcounter.counterValue;
		}
	}
	
	public static void main(String[] args) { //test main
		manager = new DBManager("votacoes");
		manager.connect();
		startDB();
	}
	
}
