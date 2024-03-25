package base;

import base.EntryPhoneAndMedia.Platform;

public class MediaSourceAndPlatform {
	 private final String mediaSource;
	    private final String platform;

	    public MediaSourceAndPlatform(String mediaSource, String platform) {
	        this.mediaSource = mediaSource;
	        this.platform = platform;
	    }

	    // Getters
	    public String getMediaSource() {
	        return mediaSource;
	    }

	    public String getPlatform() {
	        return platform;
	    }

}
