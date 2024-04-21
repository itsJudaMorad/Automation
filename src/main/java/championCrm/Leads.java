package championCrm;

public class Leads {
    public String processNumber; // מספר תהליך
    public String urgency; // דחיפות
    public String customer; // לקוח
    public String customerNumber; // מספר לקוח
    public String phone; // טלפון
    public String openingDate; // תאריך פתיחה
    public String campaignName; // שם קמפיין
    public String leadSource;
    public String closureDate; // תאריך סגירה
    public String closureReason; // סיבת סגירה
    public String salesProcessStatus; // מצב תהליך מכירה
    public String salesProcessStage; // שלב תהליך מכירה
    public String openingUser; // משתמש פותח
    public String brand; // מותג
    public String openingBrand; // מותג פותח
    public String agent; // סוכן
    public String openingAgent; // סוכן פותח
    public String modelGroup; // קב' דגם
    public String handlingUser; // משתמש מטפל
    public String vehicleQuantity; // כמות רכבים
    public String licenseNumber; // מספר רישוי
    
    public static String[] headers = {
    	    "processNumber", // מספר תהליך
    	    "urgency", // דחיפות
    	    "customer", // לקוח
    	    "customerNumber", // מספר לקוח
    	    "phone", // טלפון
    	    "openingDate", // תאריך פתיחה
    	    "campaignName", // שם קמפיין
    	    "leadSource", // מקור התהליך / הליד
    	    "closureDate", // תאריך סגירה
    	    "closureReason", // סיבת סגירה
    	    "salesProcessStatus", // מצב תהליך מכירה
    	    "salesProcessStage", // שלב תהליך מכירה
    	    "openingUser", // משתמש פותח
    	    "brand", // מותג
    	    "openingBrand", // מותג פותח
    	    "agent", // סוכן
    	    "openingAgent", // סוכן פותח
    	    "modelGroup", // קב' דגם
    	    "handlingUser", // משתמש מטפל
    	    "vehicleQuantity", // כמות רכבים
    	    "licenseNumber" // מספר רישוי
    	};
    
    public static String setMediaSourceByRules(Leads lead) {
    	String leadSource = lead.leadSource.toLowerCase();
    	if(leadSource.contains("lead-lead")) {
    		leadSource = "direct or unknown";
    	} else if (leadSource.contains("mobile-facebook")) {
    		leadSource = "54";
    	} else if (leadSource.contains("mobile-google")) {
    		leadSource = "12";
    	}else if (leadSource.contains("mobile-taboola")) {
    		leadSource = "60";
    	}
    	return leadSource;
    }
}
