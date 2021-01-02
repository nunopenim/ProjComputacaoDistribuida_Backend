package maven.projetoCD;

public class AuthManager {
	static final String defaultBase = "ou=dev,dc=example,dc=com";
	
	public boolean validSession(LoginSession session) {
		return LDAPUtils.validDn(session.getSession());
	}
	
	public boolean authenticate(LoginSession session) {
		return LDAPUtils.auth(session.getSession(), session.getPW());
	}
	
	public String search(String dn) {
		try {
			return LDAPUtils.testSearch(dn);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public LoginSession createSession(String user_id, String user_pw, boolean admin) {
		String userGroup;
		if (admin) {
			userGroup = "ou=admin";
		}
		else {
			userGroup = "ou=user";
		}
		return new LoginSession(user_id, user_pw, defaultBase, userGroup);
	}
	
}
