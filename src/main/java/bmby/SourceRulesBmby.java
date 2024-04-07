package bmby;

public class SourceRulesBmby {

	public static String setSourceMediaByRules(LeadBmby leadBmby) {
		String sourceMedia = leadBmby.getMedia();
		if(sourceMedia.contains("פייסבוק")) {
			sourceMedia ="facebook_leadgen";
		} else if (leadBmby.getMedia().contains("גוגל")) {
			sourceMedia= "google_brand";
		} else if (sourceMedia.equals("google_tlv")) {
			sourceMedia = "google_brand_tlv";
		} else if (sourceMedia.length() == 0) {
			sourceMedia = "אורגני";
		} else if (sourceMedia.contains("pmax")) {
			sourceMedia = "pmax";
		}
		return sourceMedia;
	}
	
}
