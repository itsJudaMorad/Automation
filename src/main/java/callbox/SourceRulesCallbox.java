package callbox;

public class SourceRulesCallbox {

	public static String setMediaSourceByRules(LeadCallbox leadCallbox) {
		String mediaSource = null;
		if(leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().length() == 0){
			mediaSource = "pmax";
		} else if (leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().contains("pmax")) {
			mediaSource = "pmax";
		} else if (leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().contains("modiin")) {
			mediaSource = "google_brand_modiin";
		} else if (leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().contains("tlv")) {
			mediaSource = "google_brand_tlv";
		} else if (leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().contains("hod")) {
			mediaSource = "google_brand_hod_hasharon";
		} else if (leadCallbox.getTags().equals("ad extension") && leadCallbox.getCampaign().contains("rishon")) {
			mediaSource = "google_brand_rishon_lezion";
		} else if (leadCallbox.getTags().contains("unknown")){
			mediaSource = "Organic";
		} else if  (leadCallbox.getCampaign().contains("brand") && leadCallbox.getCampaign().contains("120")) {
			mediaSource = "google_brand";
		} else if  (leadCallbox.getCampaign().contains("category") && leadCallbox.getCampaign().contains("120")) {
			mediaSource = "google_category";
		} else if (leadCallbox.getTags() != "ad extension") {
			mediaSource = leadCallbox.getTags();
		}
		return mediaSource;
	}
}