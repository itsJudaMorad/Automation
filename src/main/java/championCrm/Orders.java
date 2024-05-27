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
	// תאריך הקצאה אחרון
	public String lastAssignmentDate;
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
			"name", // שם לקוח
			"customerStatus", // סטטוס לקוח
			"phone1", // טלפון 1
			"phone2", // טלפון 2
			"openingDate", // תאריך פתיחה
			"lastAssignmentDate", // טלפון 2
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
				System.out.println("lead phone: "+lead.phoneNumber+ " orders nums: " +order.phone1 +" | "+ order.phone2);
				if (order.phone1.contains(lead.phoneNumber) || order.phone2.contains(lead.phoneNumber)) {
					lead.isLeadPlacedOrder = true;
					lead.carModel = order.modelGroupDescription;
					lead.carSubModel = order.modelDescription;
					break;
				}
			}
		}
		
		return leads;
	}
	public static List<Orders> convertOrdersType(List<VwcvOrders> vwcvOrders) {
		List<Orders> convertedOrders = new ArrayList<Orders>();
		
		for (VwcvOrders vwcvOrder : vwcvOrders) {
			Orders order = new Orders();
			order.phone1 = vwcvOrder.getPhone1();
			order.phone2 = vwcvOrder.getPhone2();
			order.model = vwcvOrder.getModel();
			order.modelDescription = vwcvOrder.getModelDescription();
			order.modelGroupDescription = vwcvOrder.getModelGroupDescription();
			convertedOrders.add(order);
		}
		return convertedOrders;
	}
	
    public static <T> List<T> selectList(List<T> list1, List<T> list2) {
        if (list1 != null) {
            return list1;
        } else if (list2 != null) {
            return list2;
        } else {
            return null; // Both lists are null
        }
    }
}
