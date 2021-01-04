package maven.projetoCD;

public class Votante {
	private String _id;
	private String _rev = null;
	private boolean votou;
	//private String itemQueVotou;
	
	public Votante (String id) {
		this._id = id;
		this.votou = false;
	}
	
	public Votante (String id, boolean votou) {
		this._id = id;
		this.votou = votou;
	}
	
	public void votar() {
		this.votou = true;
	}
	
	public boolean hasVoted() {
		return this.votou;
	}
	
}
