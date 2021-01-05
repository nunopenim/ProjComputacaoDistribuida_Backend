package maven.projetoCD;

public class Votante {
	private String _id;
	private String _rev = null;
	private String ldapId;
	private boolean votou;
	//private String itemQueVotou;
	
	public Votante (String id, String ldapID) {
		this._id = id;
		this.ldapId = ldapID;
		this.votou = false;
	}
	
	public Votante (String id, String ldapID, boolean votou) {
		this._id = id;
		this.ldapId = ldapID;
		this.votou = votou;
	}
	
	public void votar() {
		this.votou = true;
	}
	
	public boolean hasVoted() {
		return this.votou;
	}
	
	public String getLdapId() {
		return ldapId;
	}
	
}
