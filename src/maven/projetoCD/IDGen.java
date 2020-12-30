package maven.projetoCD;

public class IDGen {
	private String _id;
	private String _rev = null;
	private int counterValue;
	private int userIDGen;
	
	public IDGen(int val, int userVal) {
		this._id = "0";
		this.counterValue = val;
		this.userIDGen = userVal;
	}
	
	public String idGenerator() {
		counterValue++;
		return "" + counterValue;
	}
	
	public String userIDGenerator() {
		String value = "" + userIDGen;
		userIDGen--;
		return value;
	}
	
}
