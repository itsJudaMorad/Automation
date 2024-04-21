package callbox;

public class SourceRulesCallbox {

	public static String setMediaSourceByRules(LeadCallbox leadCallbox) {
		String mediaSource = leadCallbox.getTags();
		if(leadCallbox.getFormName().contains("Facebook")) {
			mediaSource = "54";
			
		}else if (leadCallbox.getTags().contains("ad extension")) {
			mediaSource = "12";
		}else if (leadCallbox.getTags().contains("www.")) {
			mediaSource = "direct or unknown";
		}
		return mediaSource;
	}
}