package maven.projetoCD;

public class LoginSession {
	private String user_pwd;
	private String dn;
	
	public LoginSession(String user_id, String user_pwd, String base, String user_rdn) {
		this.dn = "uid=" + user_id + "," + user_rdn + "," + base;
		this.user_pwd = user_pwd;
	}
	
	public String getSession() {
		return dn;
	}
	
	public String getPW() {
		return user_pwd;
	}
	
}
