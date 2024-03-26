package base;

public class SummaryLead {

	public String phoneNumber;
	public MediaSourceAndPlatform sourceAndPlatform;
	public boolean isRellevant;
	public boolean isSechedule;
	public boolean isDone;
	
	public SummaryLead(String phoneNumber, MediaSourceAndPlatform sourceAndPlatform) {
		this.phoneNumber = phoneNumber;
		this.sourceAndPlatform = sourceAndPlatform;
		
	}
}
