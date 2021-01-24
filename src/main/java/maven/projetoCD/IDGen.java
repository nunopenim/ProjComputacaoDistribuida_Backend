package maven.projetoCD;

public class IDGen {
	private String _id;
	private String _rev = null;
	public int counterValue;
	public int userIDGen;
	public long startTime;
	public long stopTime;
	public int voteCount;
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getStopTime() {
		return stopTime;
	}
	public void setStartTime(long a) {
		startTime = a;
	}
	public void setStopTime(long a) {
		stopTime = a;
	}
	public int getVoteCount() {
		return voteCount;
	}
	public void incVoteCount() {
		voteCount++;
	}
	
	public IDGen(int val, int userVal, long startTime, long stopTime, int voteCount) {
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
