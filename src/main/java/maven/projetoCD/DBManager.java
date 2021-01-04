package maven.projetoCD;

import java.net.URL;
import java.util.List;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class DBManager {
	
	private CloudantClient dbClient = null;
	private String dbName; 
	private Database db = null;
	
	public DBManager(String name) {
		this.dbName = name;
	}
	
	public boolean connect() {
		return connect("http://localhost:5984", "admin", "admin");
	}
	
	public boolean connect(String url, String uname, String pw) {
		try {
			dbClient = ClientBuilder.url(new URL(url)).username(uname).password(pw).build();
			return true;
		}
		catch (Exception e) {
			dbClient = null;
			return false;
		}
	}
	
	public boolean nameExists() {
		List<String> databases = dbClient.getAllDbs();
		boolean present = false;
		for (String str : databases) {
			if (dbName.equals(str)) {
				present = true;
			}
		}
		return present;
	}
	
	public Object findObj(Class cls, String id) {
		Object obj = db.find(cls, id);
		return obj;
	}
	
	public void initConnection() {
		db = dbClient.database(dbName, true);
	}
	
	public boolean addItem(Object obj) {
		try {
			db.save(obj);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeItem(Object obj) {
		try {
			db.remove(obj);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean updateItem(Object obj) {
		try {
			db.update(obj);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
