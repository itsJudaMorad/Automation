package championCrm;

import java.util.ArrayList;
import java.util.List;

import base.ChampionLead;
import base.PhoneNumberHandler;

public class Orders {
	// שם לקוח
	public String name;
	// סטטוס לקוח
	public int status;
	// טלפון 1
	public String phone1;
	// טלפון 2
	public String phone2;
	// תאריך פתיחה
	public String openingDate;
	// יצרן
	public String manufacturer;
	// שנת דגם
	public String modelYear;
	// קבוצת דגם
	public String modelGroup;
	// תאור קבוצת דגם
	public String modelGroupDescription;
	// דגם
	public String model;
	// תאור דגם
	public String modelDescription;
	// צבע
	public String color;
	// דואר אלקטרוני
	public String email;
	// שם לקוח רישוי
	public String licensingName;

	public static String[] headers = {
			"customerName", // שם לקוח
			"customerStatus", // סטטוס לקוח
			"phone1", // טלפון 1
			"phone2", // טלפון 2
			"openingDate", // תאריך פתיחה
			"manufacturer", // יצרן
			"modelYear", // שנת דגם
			"modelGroup", // קבוצת דגם
			"modelGroupDescription", // תאור קבוצת דגם
			"model", // דגם
			"modelDescription", // תאור דגם
			"color", // צבע
			"email", // דואר אלקטרוני
			"licensingName" // שם לקוח רישוי
	};

	public static  List<ChampionLead> getLeadOrder(List<Orders> allOrders, List<ChampionLead> leads) {
		for (ChampionLead lead : leads) {
			for(Orders order : allOrders) {
				if (order.phone1.contains(lead.phoneNumber) || order.phone2.contains(lead.phoneNumber)) {
					lead.isLeadPlacedOrder = true;
					break;
				}
			}
		}
		return leads;
	}

}
