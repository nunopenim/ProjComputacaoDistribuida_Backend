package maven.projetoCD;

public class AuthManager {
	static final String defaultBase = "ou=dev,dc=example,dc=com";
	
	public static boolean validSession(LoginSession session) {
		return LDAPUtils.validDn(session.getSession());
	}
	
	public static String getName(LoginSession session) {
		return LDAPUtils.printDnAttributes(session.getSession());
	}
	
	public static boolean authenticate(LoginSession session) {
		return LDAPUtils.auth(session.getSession(), session.getPW());
	}
	
	public static String search(String dn) {
		try {
			return LDAPUtils.testSearch(dn);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static void disconnect() {
		LDAPUtils.unBindConnection();
	}
	
	public static LoginSession createSession(String user_id, String user_pw, boolean admin) {
		String userGroup;
		if (admin) {
			userGroup = "ou=admin";
		}
		else {
			userGroup = "ou=people";
		}
		return new LoginSession(user_id, user_pw, defaultBase, userGroup);
	}
	
}
