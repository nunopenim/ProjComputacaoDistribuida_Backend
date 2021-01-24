package maven.projetoCD;


import java.io.IOException;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchRequestImpl;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;

public class LDAPUtils {

	//static Logger log = Logger.getLogger(LDAPUtils.class);
	static String host = "localhost";
	static int port = 10389;
	static boolean useSSL = false;
	protected static LdapConnection connection = null;

	public static LdapConnection getConnection(String host, int port, boolean useSSL) throws LdapException {
		LdapConnection c;
		//BasicConfigurator.configure();
		c = new LdapNetworkConnection(host, port, useSSL);
		c.setTimeOut(0);
		return c;
	}
	
	public static void unBindConnection() {
		try {
			connection.unBind();
		} catch (LdapException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean auth(String dn, String password) {
		try {
			connection = getConnection(host, port, useSSL);
			connection.bind(dn, password);
		} catch (LdapException e) {
			e.printStackTrace();
		}
		if (connection.isAuthenticated()) {
			return true;
		} else {
			return false;
		}	
	}

	public static boolean testFilter(String dn, String filter) throws Exception {
		SearchRequest req = new SearchRequestImpl();
		req.setScope( SearchScope.SUBTREE );
		req.addAttributes("*");
		req.setTimeLimit(0);
		req.setBase(new Dn(dn));
		req.setFilter(filter);
		SearchCursor searchCursor = connection.search(req);
		while (searchCursor.next()) {
			Response response = searchCursor.get();
			if (response instanceof SearchResultEntry){
				return true;
			}
		}
		return false;
	}

	public static void printFilterEntry(String dn, String filter) throws Exception {
		SearchRequest req = new SearchRequestImpl();
		req.setScope( SearchScope.SUBTREE );
		req.addAttributes("*");
		req.setTimeLimit(0);
		req.setBase(new Dn(dn));
		req.setFilter(filter);
		SearchCursor searchCursor = connection.search(req);
		while (searchCursor.next()) {
			Response response = searchCursor.get();
			if (response instanceof SearchResultEntry){
				Entry resultEntry = ((SearchResultEntry) response).getEntry();
				System.out.println(resultEntry);
			}
		}
	}

	public static void printEntry(String dn) throws Exception {
		SearchRequest req = new SearchRequestImpl();
		req.setScope( SearchScope.SUBTREE );
		req.addAttributes("*");
		req.setTimeLimit(0);
		req.setBase(new Dn(dn));
		SearchCursor searchCursor = connection.search(req);
		while (searchCursor.next()) {
			Response response = searchCursor.get();
			if (response instanceof SearchResultEntry){
				Entry resultEntry = ((SearchResultEntry) response).getEntry();
				System.out.println(resultEntry);
			}
		}
	}

	public static boolean testDnAttributeValue(String dn, String attribute, String value) {
		Dn objectDn = null;
		Entry cursor = null;
		try {
			objectDn = new Dn(dn);
			cursor = connection.lookup(objectDn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cursor.contains(attribute, value))
			return true;
		else 
			return false;
	}

	public static void testDn(String dn) throws Exception {
		connection = getConnection(host, port, useSSL);
		connection.bind();
		Dn objectDn = null;
		Entry cursor = null;
		objectDn = new Dn(dn);
		cursor = connection.lookup(objectDn);
		cursor.clear();
		connection.unBind();
	}

	public static boolean validDn(String dn) {
		try {
			testDn(dn);
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String printDnAttributes(String dn) {
		String name= null;
		Dn objectDn = null;
		Entry cursor = null;
		try {
			objectDn = new Dn(dn);
			cursor = connection.lookup(objectDn);
			name = cursor.get("cn").getString();
		} catch (Exception e) {
			return null;
		}
		cursor.clear();
		return name;
	}
	
	public static String testSearch(String dn) throws Exception {
	    String finalStr = null;
		EntryCursor cursor = connection.search( dn, "(objectclass=*)", SearchScope.ONELEVEL );
	    for ( Entry entry : cursor ){
	    	if (finalStr == null) {
	    		finalStr = "";
	    	}
	        //assertNotNull( entry );
	        finalStr += entry + "/n";
	    }
	    cursor.close();
	    return finalStr;
	}

}
