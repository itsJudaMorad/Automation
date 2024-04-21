package championCrm;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ZapierLeads {
	public String name; // name
	public String phone; // phone
	public String email; // email
	public String date; // date
	public String formName; // form name
	public String campaignName; // campaign name
	public String adSetName; // ad set name
	public String adName; // ad name
	
	public static String[] headers = {
		    "name", // name
		    "phone", // phone
		    "email", // email
		    "date", // date
		    "formName", // form name
		    "campaignName", // campaign name
		    "adSetName", // ad set name
		    "adName" // ad name
		};
	   public static boolean isDateFromLastMonth(String dateString) {
	        // Adjusted date format to match the input format
	        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	        // Parse the date string
	        DateTime dateTime;
	        try {
	            dateTime = formatter.parseDateTime(dateString);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Invalid date format: " + dateString);
	            return false;
	        }

	        // Get the current date and time
	        DateTime now = new DateTime();

	        // Get the last month
	        DateTime lastMonth = now.minusMonths(2);

	        // Check if the given date is from last month
	        return dateTime.getMonthOfYear() == lastMonth.getMonthOfYear() &&
	               dateTime.getYear() == lastMonth.getYear();
	    }
	
}
