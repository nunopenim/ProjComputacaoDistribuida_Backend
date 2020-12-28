package maven.projetoCD;

public class IDGen {
	private String _id;
	private String _rev = null;
	public int counterValue;
	
	public IDGen(int val) {
		this._id = "0";
		this.counterValue = val;
	}
	
}
