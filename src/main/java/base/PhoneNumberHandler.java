package base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberHandler {

	   public static boolean containsPhoneNumber(String text) {
		      // Regex to match various phone number formats
	        // Includes numbers starting with + and country code, numbers with or without leading zeros, and varying lengths
	        String phonePattern = "(\\+\\d{1,4}\\s?\\d{1,3}\\s?\\d{4,})|(\\d{9,10})";
	        
	        // Compile the regex pattern
	        Pattern pattern = Pattern.compile(phonePattern);
	        // Create a matcher to find matches in the input text
	        Matcher matcher = pattern.matcher(text);
	        
	        // Return true if any match is found
	        return matcher.find();
	    }
	public static String extractDigits(String phoneNumber) {
		// Define regular expressions to match phone number formats
		String pattern1 = "0?([1-9][0-9]{8})"; // for format "0548421854"
		String pattern2 = "(972)?([1-9][0-9]{8})"; // for format "972548421854"
		String pattern3 = "0?([1-9][0-9]{7})"; // for format "039768226"
		String pattern4 = "(972)?([1-9][0-9]{7})"; // for format "97239768226"

		// Compile patterns
		Pattern regex1 = Pattern.compile(pattern1);
		Pattern regex2 = Pattern.compile(pattern2);
		Pattern regex3 = Pattern.compile(pattern3);
		Pattern regex4 = Pattern.compile(pattern4);

		// Match phone number to patterns
		Matcher matcher1 = regex1.matcher(phoneNumber);
		Matcher matcher2 = regex2.matcher(phoneNumber);
		Matcher matcher3 = regex3.matcher(phoneNumber);
		Matcher matcher4 = regex4.matcher(phoneNumber);

		// Check if the phone number matches a pattern
		if (matcher1.matches()) {
			// Extract only the digits from the matched phone number
			return matcher1.group(1);
		} else if (matcher2.matches()) {
			// Extract only the digits from the matched phone number
			return matcher2.group(2);
		} else if (matcher3.matches()) {
			// Extract only the digits from the matched phone number
			return matcher3.group(1);
		} else if (matcher4.matches()) {
			// Extract only the digits from the matched phone number
			return matcher4.group(2);
		} else {
			// If the phone number doesn't match any pattern, return it as is
			return phoneNumber;
		}
	}
	
	public static String removeDashFromPhoneNumber(String phoneNumber) {
		return phoneNumber.replace("-", "");
	}
}
