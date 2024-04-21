package championCrm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.ChampionLead;
import base.PhoneNumberHandler;

public class Process {
	public String processNumber; // מספר תהליך
	public String urgency; // דחיפות
	public String customer; // לקוח
	public String customerNumber; // מספר לקוח
	public String phone; // טלפון
	public String openingDate; // תאריך פתיחה
	public String campaignName; // שם קמפיין
	public String processSource; // מקור התהליך / הליד
	public String processStage; // שלב בתהליך
	public String daysInStage; // ימים בשלב
	public String daysSinceLastActivity; // ימים מסגירת פעילות אחרונה
	public String closureDate; // תאריך סגירה
	public String closureReason; // סיבת סגירה
	public String openingUser; // משתמש פותח
	public String brand; // מותג
	public String openingBrand; // מותג פותח
	public String agent; // סוכן
	public String openingAgent; // סוכן פותח
	public String modelGroup; // קב' דגם
	public String handlingUser; // משתמש מטפל
	public String vehicleQuantity; // כמות רכבים

	public static String[] headers = {
			"processNumber", // מספר תהליך
			"urgency", // דחיפות
			"customer", // לקוח
			"customerNumber", // מספר לקוח
			"phone", // טלפון
			"openingDate", // תאריך פתיחה
			"campaignName", // שם קמפיין
			"processSource", // מקור התהליך / הליד
			"processStage", // שלב בתהליך
			"daysInStage", // ימים בשלב
			"daysSinceLastActivity", // ימים מסגירת פעילות אחרונה
			"closureDate", // תאריך סגירה
			"closureReason", // סיבת סגירה
			"openingUser", // משתמש פותח
			"brand", // מותג
			"openingBrand", // מותג פותח
			"agent", // סוכן
			"openingAgent", // סוכן פותח
			"modelGroup", // קב' דגם
			"handlingUser", // משתמש מטפל
			"vehicleQuantity" // כמות רכבים
	};
	public static Map<String, List<String>> getTheDuplicatesLeadInProcessList(List<Process> process){
		Map<String, List<String>> phoneToProcessStages = new HashMap<>();

        for (Process proc: process) {
        	if(proc.phone == null) {
        		break;
        	}
            // Check if the map already contains the phone number
            if (!phoneToProcessStages.containsKey(proc.phone)) {
                // If not, create a new list and add the processStage, then put it in the map
                List<String> stages = new ArrayList<>();
                stages.add(proc.processStage);
                phoneToProcessStages.put(proc.phone, stages);
            } else {
                // If it does, simply add the processStage to the existing list
                phoneToProcessStages.get(proc.phone).add(proc.processStage);
            }
        }

        return phoneToProcessStages;
	}
	
	public static String getHighestPriorityStage(List<String> stages) {
        // The lower the index, the higher the priority
		List<String> priorityList = Arrays.asList("שחרור", "מסירה", "הזמנה", "הצעת מחיר מסחרית", "פגישה ראשונה", "הזדמנות");
		int highestPriorityIndex = Integer.MAX_VALUE;
        String highestPriorityStage = "";
        
        for (String stage : stages) {
            int currentIndex = priorityList.indexOf(stage);
            if (currentIndex != -1 && currentIndex < highestPriorityIndex) {
                highestPriorityIndex = currentIndex;
                highestPriorityStage = stage;
            }
        }
        
        return highestPriorityStage;
    }
	
	public static  List<ChampionLead> getLeadProcess(List<Process> allProcess, List<ChampionLead> leads) {
		 Map<String, List<String>> phoneToProcessStages = getTheDuplicatesLeadInProcessList(allProcess);
		for (ChampionLead lead : leads) {
			for(Map.Entry<String, List<String>> entry : phoneToProcessStages.entrySet()) {
				if(PhoneNumberHandler.removeDashFromPhoneNumber(entry.getKey()).contains(lead.phoneNumber)){
					lead.process = getHighestPriorityStage(entry.getValue());
					break;
				}
			}
		}
		return leads;
	}

	public static void countTheNumberOfProcess(ChampionLead lead) {
		
		if (lead.isLeadPlacedOrder) {
			lead.isLeadOpportunity = 1;
			lead.isLeadSetMeeting = 1;
			lead.isLeadOrdered = 1;
		}else if(lead.process == null) {
			lead.isLeadOpportunity = 0;
			lead.isLeadOrdered = 0;
			lead.isLeadSetMeeting  = 0;
		}else if (lead.process.contains("פגישה") || lead.process.contains("הצעת מחיר")) {
			lead.isLeadOpportunity = 1;
			lead.isLeadSetMeeting  = 1;
			lead.isLeadOrdered = 0;
		} else if (lead.process.contains("הזדמנות")) {
			lead.isLeadOpportunity = 1;
			lead.isLeadOrdered = 0;
			lead.isLeadSetMeeting  = 0;
		} 
			
		}
}