package base;

public class EntryPhoneAndMedia {
	private String phoneNumber;
	private String mediaSource;

	public EntryPhoneAndMedia(String phoneNumber, String mediaSource) {
		this.phoneNumber = phoneNumber;
		this.mediaSource = mediaSource;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMediaSource() {
		return mediaSource;
	}
}
