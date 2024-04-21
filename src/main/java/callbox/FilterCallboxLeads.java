package callbox;

public class FilterCallboxLeads {
	
	public static boolean validateLeadIsNotTest(LeadCallbox lead) {
		if(lead.getName().contains("בדיקה") || 
			lead.getName().contains("בודקת") || 
			lead.getName().contains("טסט") || 
			lead.getName().contains("test") || 
			lead.getEmail().contains("9-9")) {
			return false;
		}else {
			return true;
		}
	}
	
	public static boolean  validateLeadDurationIsAbove30Sec(LeadCallbox lead) {
		return convertToSeconds(lead.getDuration()) >= 30 ? true : false;
	}
	 public static int convertToSeconds(String time) {
	        String[] parts = time.split(":");
	        int hours = Integer.parseInt(parts[0]);
	        int minutes = Integer.parseInt(parts[1]);
	        int seconds = Integer.parseInt(parts[2]);
	        return (hours * 3600) + (minutes * 60) + seconds;
	    }
}
