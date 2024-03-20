package bmby;

public class SourceRulesBmby {

	public static String setSourceMediaByRules(LeadBmby leadBmby) {
		String sourceMedia = leadBmby.getMedia();
		if(sourceMedia.contains("פייסבוק")) {
			sourceMedia ="facebook_leadgen";
		} else if (leadBmby.getMedia().contains("גוגל")) {
			sourceMedia= "google_brand";
		} else if (sourceMedia.length() == 0) {
			sourceMedia = "אורגני";
		}
		return sourceMedia;
	}
	
}
