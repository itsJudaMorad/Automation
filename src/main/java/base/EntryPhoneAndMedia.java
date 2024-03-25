package base;

public class EntryPhoneAndMedia {

	public enum Platform{
		BMBY, CALLBOX
	}
	public enum LeadStatus{
		DONE, SCHEDULED, RELEVANT
	}
	
	private String phoneNumber;
	private String mediaSource;
	private String mediaPlatform;
	private String leadStatus;

	public EntryPhoneAndMedia(String phoneNumber, String mediaSource, Platform platform) {
		this.phoneNumber = phoneNumber;
		this.mediaSource = mediaSource;
		this.setMediaPlatform(platform.name());
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMediaSource() {
		return mediaSource;
	}

	public String getMediaPlatform() {
		return mediaPlatform;
	}

	public void setMediaPlatform(String mediaPlatfom) {
		this.mediaPlatform = mediaPlatfom;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	
}
