package maven.projetoCD;

public class IDGen {
	private String _id;
	private String _rev = null;
	public int counterValue;
	public int userIDGen;
	public int startTime;
	public int stopTime;
	public int voteCount;
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getStopTime() {
		return stopTime;
	}
	public void setStartTime(int a) {
		startTime = a;
	}
	public void setStopTime(int a) {
		stopTime = a;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void incVoteCount() {
		voteCount++;
	}
	
	public IDGen(int val, int userVal, int startTime, int stopTime, int voteCount) {
		this._id = "0";
		this.counterValue = val;
		this.userIDGen = userVal;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.voteCount = voteCount;
	}
	
	public String idGenerator() {
		counterValue++;
		return "" + counterValue;
	}
	
	public String userIDGenerator() {
		userIDGen--;
		return "" + userIDGen;
	}
	
}
