package championCrm;

import java.util.Arrays;
import java.util.List;

import callbox.LeadCallbox;

public class CarBrand {
	 public static boolean containsAnySubBrand(String text, Brand brand) {
	        for (String subBrand : brand.getSubBrands()) {
	            if (text.toLowerCase().equals(subBrand.toLowerCase())) {
	                return true;  // Return true as soon as a match is found
	            }
	        }
	        return false;  // Return false if no sub-brands are found in the text
	    }
	 

		public static Brand findCarBrand(List<LeadCallbox> callboxLeads) {
			String Url = callboxLeads.stream().filter(lead -> lead.getPage().contains("co.il")).findFirst().orElse(null).getPage();
			Brand brand = null;
			if(Url.contains("vwcv")) {
				brand = Brand.VWCV;
			} else if (Url.contains("seat")) {
				brand = Brand.SEAT;
			} else if (Url.contains("cupra")) {
				brand = Brand.CUPRA;
			}
			return brand;
		}
	public enum Brand {
	    SEAT("IBIZA", "ARONA", "ATECA", "IBIZA NEW","LEON"),
	    CUPRA("CUPRA LEON", "LEON", "CUPRA ATECA", "CUPRA FORMENTOR"),
	    VWCV("Caddy", "Amarok", "Transporter");

	    private List<String> subBrands;

	    // Constructor for the enum to accept sub-brands
	    Brand(String... subBrands) {
	        this.subBrands = Arrays.asList(subBrands);
	    }
	    public List<String> getSubBrands() {
	        return subBrands;
	    }
	    // Method to check if a string contains any of the sub-brands of a given brand
	   
	}
}
